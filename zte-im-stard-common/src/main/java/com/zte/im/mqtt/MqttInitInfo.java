package com.zte.im.mqtt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttInitInfo {
	
	private final static Logger logger = LoggerFactory.getLogger(MqttInitInfo.class);
	
    private String mServerIp = null;
    private int mServerPort = 0;
    private Socket mConnectHandle = null;
    private DataInputStream in = null;
    private OutputStream out = null;

    private boolean nIsSocketError = true;
    private Object mSocketErrorSyncToken = new Object();

    public final static int CONNECTED = 0;
    public final static int CONNECTING = 1;
    public final static int CLOSING = 2;
    public final static int CLOSED = 4;
    private int mConnectStatus = CLOSED; // 链接状态
    private Object mStatusSyncToken = new Object();

    public final static int MAX_RETRY_TIMES = 3;

    private int mConnectServerFailedTimes = 0;
    private int mIdleTime = 3;
    private String mClientId = "999";
    private String mDeviceid = null;
    private int mClientQos = 0;
    private int mHeartBeatInterval = 60;
    private Timer mHeartBeatTimer = null;
    long current_time = System.currentTimeMillis();

    private Queue<MqttMessageBase> mRecvMessageQueue = new LinkedList<MqttMessageBase>();
    private Lock mRecvQueueLock = new ReentrantLock();
    private Condition mRecvQueueCondition = mRecvQueueLock.newCondition();

    private Queue<MqttMessageBase> mSendMessageQueue = new LinkedList<MqttMessageBase>();
    private Lock mSendQueueLock = new ReentrantLock();
    private Condition mSendQueueCondition = mSendQueueLock.newCondition();

    private Lock mNetworkLock = new ReentrantLock();
    private Condition mNetworkCondition = mNetworkLock.newCondition();

    private Lock mSocketLock = new ReentrantLock();
    private Condition mSocketCondition = mSocketLock.newCondition();
    
//    private static boolean mKickOff = false;
    
    public MqttInitInfo(String ip, int port, int qos, int heart_beat_interval, String client_id, String device_id) {
        mServerIp = ip;
        mServerPort = port;
        mClientQos = qos;
        mHeartBeatInterval = heart_beat_interval;
        mClientId = client_id;
        mDeviceid = device_id;
    }

    public boolean is_server_error() {
        return mConnectServerFailedTimes >= MAX_RETRY_TIMES;
    }
    
    public void set_kick_off()
    {
//    	MqttInitInfo.mKickOff = true;
    }
    
    public void cancle_kick_off()
    {
//    	MqttInitInfo.mKickOff = false;
    }

    public void wait_for_network_ok() {
        MqttUtils.i("MqttThread", "wait_for_network_ok.");
        mNetworkLock.lock();
        try {
            mNetworkCondition.await();
        } catch (InterruptedException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "wait_for_network_ok mNetworkCondition await Exception [" + e.toString() + "].");
        } finally {
            mNetworkLock.unlock();
        }
    }

    public void notify_network_ok() {
        MqttUtils.i("MqttThread", "notify_network_ok.");
        mNetworkLock.lock();
        try {
            mNetworkCondition.signalAll();
        } finally {
            mNetworkLock.unlock();
        }
    }

    public void wait_for_socket_ok() {
        MqttUtils.i("MqttThread", "wait_for_socket_ok.");
        mSocketLock.lock();
        try {
            mSocketCondition.await();
        } catch (InterruptedException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "wait_for_socket_ok mSocketCondition await Exception [" + e.toString() + "].");
        } finally {
            mSocketLock.unlock();
        }
    }

    public void notify_socket_ok() {
        MqttUtils.i("MqttThread", "notify_socket_ok.");
        mSocketLock.lock();
        try {
            mSocketCondition.signalAll();
        } finally {
            mSocketLock.unlock();
        }
    }

    public void switch_client_id(String client_id) {
        long num = 0;
        try {
        	num = Long.valueOf(client_id);
        } catch (NumberFormatException ex) {
            MqttUtils.i("MqttThread", "switch_client_id old client id is [" + mClientId + "] new client id is ["
                    + client_id + "], not a long.");
            return;
        }

        if (num <= 0) {
            MqttUtils.i("MqttThread", "switch_client_id old client id is [" + mClientId + "] new client id is ["
                    + client_id + "], not a long.");
            return;
        }

        if ((null != mClientId) && (mClientId.equalsIgnoreCase(client_id))) {
            // same client id do nothing
            MqttUtils.i("MqttThread", "switch_client_id old client id is [" + mClientId + "] new client id is ["
                    + client_id + "], need do nothing.");
            return;
        }

        MqttUtils.i("MqttThread", "switch_client_id old client id is [" + mClientId + "] new client id is [" + client_id
                + "], socket need reconnect.");
        mClientId = client_id;
        set_is_socket_error();
    }

    public DataInputStream get_in() {
        return in;
    }

    public OutputStream get_out() {
        try {
			return mConnectHandle.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return null;
		}
    }

    public void resend(MqttMessageBase message) {
        if (message.get_retry_times() >= MqttInitInfo.MAX_RETRY_TIMES) {
            MqttUtils.e("MqttThread", "mqtt message[" + message.toString()
                    + "] failed and extern max retry times, do error response!");
            error_response(message);
        } else {
            MqttUtils.e("MqttThread", "mqtt message[" + message.toString() + "] failed and need retry!");
            message.inc_retry_times();
            put_send_message(message);
        }
    }

    public void start_heart_beat_timer() {
        mHeartBeatTimer = new Timer();
        mHeartBeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                heartbeat();
            }
        }, mHeartBeatInterval * 1000, mHeartBeatInterval * 1000);
    }

    public boolean can_send() {
        synchronized (mSocketErrorSyncToken) {
            return !nIsSocketError && MqttUtils.isNetConnected();
        }
    }

    public boolean get_is_network_error() {
        return MqttUtils.isNetConnected() ? false : true;
    }

    public boolean get_is_socket_error() {
        synchronized (mSocketErrorSyncToken) {
            return nIsSocketError;
        }
    }

    public void set_is_socket_error() {
        synchronized (mSocketErrorSyncToken) {
            nIsSocketError = true;
        }
        boolean is_connected = false;
        synchronized (mStatusSyncToken) {
            is_connected = (CONNECTED == mConnectStatus);
        }
        if (is_connected) {
            MqttUtils.i("MqttThread", "set_is_socket_error set mConnectStatus CLOSED!");
            synchronized (mStatusSyncToken) {
                mConnectStatus = CLOSED;
            }
            disconnect();
        } else {
            MqttUtils.i("MqttThread", "set_is_socket_error mConnectStatus is not CONNECTED!");
        }
    }
    
    public void socket_close() {
        synchronized (mSocketErrorSyncToken) {
            nIsSocketError = true;
        }
        boolean is_connected = false;
        synchronized (mStatusSyncToken) {
            is_connected = (CONNECTED == mConnectStatus);
        }
        if (is_connected) {
            MqttUtils.i("MqttThread", "socket_close set mConnectStatus CLOSED!");
            
            // 发送 connect 消息 等待响应
            MqttDisconnect discon_msg = new MqttDisconnect();

            try {
                byte[] bytes = discon_msg.encode();
                if (null == bytes) {
                    MqttUtils.i("MqttThread", "MqttDisconnect encode failed!");
                    return;
                }
                if (null != out) {
                    out.write(bytes, 0, bytes.length);
                    out.flush();
                    MqttUtils.i("MqttThread", "send a disconnect message [" + discon_msg.toString() + "] to server["
                            + mServerIp + ":" + mServerPort + "].");
                }
            } catch (IOException e) {
                logger.error("", e);
                MqttUtils.i("MqttThread", "send connect message to server IOException [" + e.toString() + "].");
            }       
            
            MqttUtils.i("MqttThread", "wait for connection closed.");
            
            synchronized (mStatusSyncToken) {
                mConnectStatus = CLOSED;
            }
            close_connect();         
        } else {
            MqttUtils.i("MqttThread", "socket_close mConnectStatus is not CONNECTED!");
        }
    }

    public void disconnect() {
        MqttUtils.i("MqttThread", "disconnect send disconnect message to server!");
        synchronized (mStatusSyncToken) {
            mConnectStatus = CLOSING;
        }
        
        // 发送 connect 消息 等待响应
        MqttDisconnect discon_msg = new MqttDisconnect();

        try {
            byte[] bytes = discon_msg.encode();
            if (null == bytes) {
                MqttUtils.e("MqttThread", "MqttDisconnect encode failed!");
                return;
            }
            if (null != out) {
                out.write(bytes, 0, bytes.length);
                MqttUtils.i("MqttThread", "send a disconnect message [" + discon_msg.toString() + "] to server["
                        + mServerIp + ":" + mServerPort + "].");
            }
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "send connect message to server IOException [" + e.toString() + "].");
        }       
        
        MqttUtils.i("MqttThread", "wait for connection closed.");
    	on_idle();
    	
    	try {
            mConnectHandle.close();
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "force close connect_handle failed. IOException [" + e.toString()
                    + "].");
        }
    	
    	synchronized (mStatusSyncToken) {
            mConnectStatus = CLOSED;
        }
    }

    public void on_idle() {
        try {
            Thread.sleep(mIdleTime * 1000);
        } catch (InterruptedException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "sleep Exception [" + e.toString() + "].");
        }
    }

    public boolean check_connect() {    	
    	/*if (MqttInitInfo.mKickOff)
    	{
    		MqttUtils.e("MqttThread", "user kick off, not need connect!");
    		return false;
    	}*/
        boolean is_closed = false;
        
        MqttUtils.d("MqttThread", "check_connect");
        
        synchronized (mStatusSyncToken) {
            is_closed = (CONNECTED != mConnectStatus);
        }
        if (is_closed || get_is_socket_error()) {
        	if (mConnectServerFailedTimes > 0)
        	{
        		MqttUtils.e("MqttThread", "check_connect sleep " + 2 * mIdleTime + " second.");
        		try {
                    Thread.sleep(2 * mIdleTime * 1000);
                } catch (InterruptedException e) {
                    logger.error("", e);
                    MqttUtils.e("MqttThread", "check_connect sleep Exception [" + e.toString() + "].");
                }
        	}
            
        	if (0 != connect()) {
                // check network
                synchronized (mStatusSyncToken) {
                    mConnectStatus = CLOSED;
                }

                if (is_server_error()) {
                    // server is error, notify send thread to send error response
                    notify_socket_ok();
                    MqttUtils.i("MqttThread", "connect to mqtt server failed " + mConnectServerFailedTimes
                            + " times, need send error response.");
                }
                return false;
            }
        } else {
            // MqttUtils.i("MqttThread", "connect_status is CONNECTED.");
        }
        
        MqttUtils.d("MqttThread", "connect ok.");
        return true;
    }
    
    public boolean is_connecting() { 
	    synchronized (mStatusSyncToken) {
	    	return (mConnectStatus == CONNECTING);
	    }
    }
    
    public int connect() {        
        boolean wait_close = false;
        synchronized (mStatusSyncToken) {
        	wait_close = (CLOSING == mConnectStatus);
        }
        
        if (wait_close)
        {
        	MqttUtils.i("MqttThread", "connect wait for connection closed.");
        	on_idle();
        	return -1;
        }
        
        MqttUtils.i("MqttThread", "Connect to [" + mServerIp + ":" + mServerPort + "]");
        
        synchronized (mStatusSyncToken) {
        	mConnectStatus = CONNECTING;
        }
        close_connect();

        try {
            // if ((null == mConnectHandle) || (mConnectHandle.isConnected()))
            // {
            // mConnectHandle = new Socket();
            // }
            
            MqttUtils.e("MqttThread", " connect mConnectHandle = new Socket()");

            mConnectHandle = new Socket();
            SocketAddress socAddress = new InetSocketAddress(mServerIp, mServerPort);
            MqttUtils.v("wocao", "wocao wo tama de lai lianjie le");
            mConnectHandle.connect(socAddress, 5000);
        } catch (UnknownHostException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "create socket UnknownHostException [" + e.toString() + "].");

            mConnectServerFailedTimes++;
            return -1;
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "create socket IOException [" + e.toString() + "].");

            mConnectServerFailedTimes++;
            return -1;
        }

        try {
        	mConnectHandle.setSoTimeout(60000);
            in = new DataInputStream(mConnectHandle.getInputStream());
            out = mConnectHandle.getOutputStream();
        }
        catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "get inputstream or outputstream IOException [" + e.toString() + "].");

            mConnectServerFailedTimes++;
            return -1;
        }
        
        String user_name = MqttUtils.getDeviceImei();
        String password = user_name;
        String will_topic = mClientId;
        String will_message = mDeviceid;
        
        MqttUtils.i("MqttThread", "connect getDeviceId---:" + will_message);
        // 发送 connect 消息 等待响应
        MqttConnect con_msg = new MqttConnect(mClientId, user_name, password, will_topic, will_message, mHeartBeatInterval);

        try {
            byte[] bytes = con_msg.encode();
            if (null == bytes) {
                MqttUtils.e("MqttThread", "MqttConnect encode failed!");

                mConnectServerFailedTimes++;
                return -1;
            }
            out.write(bytes, 0, bytes.length);
            MqttUtils.i("MqttThread", "send a connect message [" + con_msg.toString() + "] to server[" + mServerIp + ":"
                    + mServerPort + "].");
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "send connect message to server IOException [" + e.toString() + "].");

            mConnectServerFailedTimes++;
            return -1;
        }

        byte first = 0;
        try {
            first = in.readByte();
        } catch (IOException e1) {
            logger.error("", e1);
            MqttUtils.e("MqttThread", "wait for connect ack, but IOException [" + e1.toString() + "].");

            mConnectServerFailedTimes++;
            return -1;
        }

        byte type = (byte) ((first >>> 4) & 0x0F);
        if (type != MqttMessageBase.MESSAGE_TYPE_CONNACK) {
            MqttUtils.e("MqttThread", "wait for connect ack, but type is [" + type + "].");

            mConnectServerFailedTimes++;
            return -1;
        }

        long remLen = 0;
        try {
            remLen = MqttMessageBase.decode_remaining_length(in);
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "read mqtt's Remaining Length, but IOException [" + remLen + "].");

            mConnectServerFailedTimes++;
            return -1;
        }

        byte[] variable_header = new byte[(int) remLen];
        try {
            in.readFully(variable_header);
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "read mqtt's variable header and payload, but IOException [" + e.toString() + "].");

            mConnectServerFailedTimes++;
            return -1;
        }

        MqttUtils.i("MqttThread", "recv a connect ack message from to server[" + mServerIp + ":" + mServerPort + "].");

        byte return_code = variable_header[1];
        if (MqttConnect.CONNECTION_ACCEPTED != return_code) {
            MqttUtils.e("MqttThread", "read mqtt's connect ack return code[" + return_code + "] is not 0.");

            mConnectServerFailedTimes++;
            return -1;
        }

        synchronized (mSocketErrorSyncToken) {
            nIsSocketError = false;
        }
        synchronized (mStatusSyncToken) {
            mConnectStatus = CONNECTED;
        }

        mConnectServerFailedTimes = 0;
        current_time = System.currentTimeMillis();
        notify_socket_ok();
        MqttUtils.i("MqttThread", "connect to mqtt server success.");
        return 0;
    }

    public synchronized void close_connect() {
        try {
            if (null != in) {
                in.close();
            }
        } catch (IOException e1) {
            logger.error("", e1);
            MqttUtils.i("MqttThread", "close_connect in close failed. IOException [" + e1.toString() + "].");
        } finally {
            in = null;
        }

        try {
            if (null != out) {
                out.close();
            }
        } catch (IOException e1) {
            logger.error("", e1);
            MqttUtils.i("MqttThread", "close_connect out close failed. IOException [" + e1.toString() + "].");
        } finally {
            out = null;
        }

        if (null != mConnectHandle) {
            if (null != mConnectHandle) {
                if (mConnectHandle.isInputShutdown()) {
                    MqttUtils.i("MqttThread", "close_connect mConnectHandle inputstream is shutdown.");
                } else {
                    MqttUtils.i("MqttThread", "close_connect mConnectHandle inputstream is not shutdown. shut it.");

                    try {
                        mConnectHandle.shutdownInput();
                    } catch (IOException e) {
                        logger.error("", e);
                        MqttUtils.i("MqttThread", "close_connect shutdownInput failed. IOException [" + e.toString()
                                + "].");
                    }
                }

                if (mConnectHandle.isOutputShutdown()) {
                    MqttUtils.i("MqttThread", "close_connect mConnectHandle outputstream is shutdown.");
                } else {
                    MqttUtils.i("MqttThread", "close_connect mConnectHandle outputstream is not shutdown. shut it.");
                    try {
                        mConnectHandle.shutdownOutput();
                    } catch (IOException e) {
                        logger.error("", e);
                        MqttUtils.i("MqttThread", "close_connect shutdownOutput failed. IOException [" + e.toString()
                                + "].");
                    }
                }

                try {
                    mConnectHandle.close();
                } catch (IOException e) {
                    logger.error("", e);
                    MqttUtils.i("MqttThread", "close_connect connect_handle close failed. IOException [" + e.toString()
                            + "].");
                }
            }
        }
    }

    public void error_response(MqttMessageBase message) {
        MqttUtils.i("MqttThread", "error_response. message type is [" + message.get_type() + "].");
        MqttMessageBase err_msg = new MqttMessageBase();
        if (MqttMessageBase.MESSAGE_TYPE_PUBLISH == message.get_type()) {
            err_msg.set_type(MqttMessageBase.MESSAGE_TYPE_PUBACK);
        } else if (MqttMessageBase.MESSAGE_TYPE_QUERY_ONLINE == message.get_type()) {
            err_msg.set_type(MqttMessageBase.MESSAGE_TYPE_QUERY_ONLINE);
            err_msg.set_if_type(MqttUtils.CHAT_QUERY_ONLINE);
        } else {
            err_msg.set_type(message.get_type());
        }
        err_msg.set_msgId(message.get_msgId());

        err_msg.set_is_failed_response();
        put_recv_message(err_msg);
    }

    public void heartbeat() {
        // send heartbeat to server
        MqttPingReq ping = new MqttPingReq();
        put_send_message(ping);
    }

    public void on_heart_beat() {
        // recv ping response message
        current_time = System.currentTimeMillis();
    }

    public boolean check_heart_beat() {
        long now = System.currentTimeMillis();
        if (now > (current_time + mHeartBeatInterval * 2000)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean put_send_message(MqttMessageBase message) {
        mSendQueueLock.lock();
        try {
            if (!mSendMessageQueue.offer(message)) {
                MqttUtils.e("MqttThread", "Send Message Queue is Full!");
                return false;
            } else {
                mSendQueueCondition.signalAll();

                MqttUtils.i("MqttThread", "put send message to Send Message Queue success![" + mSendMessageQueue.size()
                        + "]");
                return true;
            }
        } finally {
            mSendQueueLock.unlock();
        }
    }

    public boolean get_send_message(Queue<MqttMessageBase> message_list) {
        mSendQueueLock.lock();
        try {
            if (mSendMessageQueue.isEmpty()) {
                mSendQueueCondition.await();
            }
            MqttUtils.d("MqttThread", "get_send_message mSendMessageQueue have " +  mSendMessageQueue.size() + " messages.");
            message_list.clear();
            message_list.addAll(mSendMessageQueue);
            mSendMessageQueue.clear();
            return true;
        } catch (InterruptedException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "get_message mSendQueueCondition await Exception [" + e.toString() + "].");
            return false;
        } finally {
            mSendQueueLock.unlock();
        }
    }

    public void put_recv_message(MqttMessageBase recv_message) {
        mRecvQueueLock.lock();
        try {
            if (!mRecvMessageQueue.offer(recv_message)) {
                MqttUtils.e("MqttThread", "put message to recv_message_queue failed!");
            } else {
                mRecvQueueCondition.signalAll();
            }
        } finally {
            mRecvQueueLock.unlock();
        }
    }

    public boolean get_recv_message(Queue<MqttMessageBase> message_list) {
        mRecvQueueLock.lock();
        try {
            if (mRecvMessageQueue.isEmpty()) {
                mRecvQueueCondition.await();
            }

            message_list.clear();
            message_list.addAll(mRecvMessageQueue);
            mRecvMessageQueue.clear();
            return true;
        } catch (InterruptedException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "get_message mRecvQueueCondition await Exception [" + e.toString() + "].");
            return false;
        } finally {
            mRecvQueueLock.unlock();
        }
    }
    
    public boolean have_recv_message() {
        mRecvQueueLock.lock();
        try {
            if (mRecvMessageQueue.isEmpty()) {
                return false;
            }
            return true;
        } finally {
            mRecvQueueLock.unlock();
        }
    }
}

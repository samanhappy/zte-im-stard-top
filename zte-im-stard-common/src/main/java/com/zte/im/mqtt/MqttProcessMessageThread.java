package com.zte.im.mqtt;

import java.util.LinkedList;
import java.util.Queue;

public class MqttProcessMessageThread {
    private MqttInitInfo mMqttInfo = null;
    private Queue<MqttMessageBase> mMessageQueue = new LinkedList<MqttMessageBase>();

    private boolean mIsStopThread = false;

    private MqttRecvThread mRecvThread = null;
    private MqttSendThread mSendThread = null;
    private OnMqttSeviceIF mOnMqttSeviceIF = null;
    private static MqttProcessMessageThread mMqttProcessMessageThread = null;

    public synchronized static MqttProcessMessageThread getInstance(MqttInitInfo info, OnMqttSeviceIF callbackif) {
        if (mMqttProcessMessageThread == null) {
            MqttUtils.i("MqttThread", "mMqttProcessMessageThread == null");
            mMqttProcessMessageThread = new MqttProcessMessageThread(info, callbackif);
        }
        return mMqttProcessMessageThread;
    }

    public MqttProcessMessageThread(MqttInitInfo info, OnMqttSeviceIF callbackif) {
        MqttUtils.i("MqttThread", "MqttProcessMessageThread construct");
        mMqttInfo = info;
        mOnMqttSeviceIF = callbackif;
        mRecvThread = new MqttRecvThread(info);
        mSendThread = new MqttSendThread(info);
        startThread();
    }

    public boolean put_message(MqttMessageBase message) {
        return mMqttInfo.put_send_message(message);
    }

    public void switch_client_id(String user_id) {
        mMqttInfo.switch_client_id(user_id);
    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsStopThread) {
                    if (mMqttInfo.get_recv_message(mMessageQueue)) {
                        process_queue(mMessageQueue);
                    }
                }
            }
        }).start();
    }

    public void process_queue(Queue<MqttMessageBase> message_list) {
        while (true) {
            MqttMessageBase message = message_list.poll();
            if (null != message) {
                // decode
                message.decode();
                byte type = message.get_type();
                switch (type) {
                case MqttMessageBase.MESSAGE_TYPE_PUBLISH: {
                    MqttUtils.i("MqttThread", "process message recved a publish message:[" + ((MqttPublishMessage) message).toString()
                            + "].");
                    
                    // send ack
                    MqttPubAck publish_ack = new MqttPubAck();
                    publish_ack.set_msgId(message.get_msgId());
                    mMqttInfo.put_send_message(publish_ack);
                    MqttUtils.i("MqttThread", "process message send a publish ack message[" + publish_ack.get_msgId() + "]");
                    
                    mOnMqttSeviceIF.messageArrived((MqttPublishMessage) message);
                    break;
                }
                case MqttMessageBase.MESSAGE_TYPE_DISCONNECT:
                {
                	MqttUtils.i("MqttThread", "call onDisconnect, server close socket.");
                	mMqttInfo.set_kick_off();
                	mOnMqttSeviceIF.onDisconnect();
                	break;
                }
                case MqttMessageBase.MESSAGE_TYPE_PUBACK:
                case MqttMessageBase.MESSAGE_TYPE_PUBREC:
                case MqttMessageBase.MESSAGE_TYPE_QUERY_ONLINE: {
                    MqttUtils.i(
                            "MqttThread",
                            "recved a ack message:[" + message.toString() + "], result["
                                    + message.get_is_failed_response() + "].");
                    if (message.get_is_failed_response()) {
                        MqttUtils.i("MqttThread", "call onFailure. message id[" + message.get_msgId() + "].");
                        mOnMqttSeviceIF.onFailure(message.get_if_type(), message.get_msgId());
                    } else {
                        MqttUtils.i("MqttThread", "call onSuccess. message id[" + message.get_msgId() + "].");
                        mOnMqttSeviceIF.onSuccess(message.get_if_type(), message.get_msgId());
                    }
                    break;
                }
                default: {
                    MqttUtils.i("MqttThread", "recv mqtt message. type is [" + type + "], do nothing.");
                    continue;
                }
                }
            } else {
                break;
            }
        }
    }

    public void onNetWorkChanged(boolean isConnect, int networkType) {
        if (isConnect) {
            MqttUtils.i("MqttThread", "onNetWorkChanged recv a network ok message, networkType is [" + networkType
                    + "].");            
            if (get_is_socket_error() && (!mMqttInfo.is_connecting()))
            {
            	mMqttInfo.notify_network_ok();
            }
        } else {
            MqttUtils.i("MqttThread", "onNetWorkChanged recv a network broken message, networkType is [" + networkType
                    + "].");
            // close socket immediately
            mMqttInfo.socket_close();
        }
    }
    
    public boolean get_is_socket_error()
    {
    	return mMqttInfo.get_is_socket_error();
    }

    public void onDestroy() {
    	MqttUtils.i("MqttThread", "Process thread onDestroy.");
    	
        mIsStopThread = true;
        mRecvThread.onDestroy();
        mSendThread.onDestroy();
        
        if (mMqttInfo.have_recv_message())
    	{
    		if (mMqttInfo.get_recv_message(mMessageQueue)) {
                process_queue(mMessageQueue);
            }
    	}
        
        mRecvThread = null;
        mSendThread = null;
        mMqttProcessMessageThread = null;
        
        mMqttInfo.close_connect();
        
    }
}

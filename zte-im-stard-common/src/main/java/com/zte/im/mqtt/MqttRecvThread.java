package com.zte.im.mqtt;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttRecvThread {
	
	private final static Logger logger = LoggerFactory.getLogger(MqttRecvThread.class);
	
    private MqttInitInfo mMqttInfo = null;

    private boolean mIsStopThread = false;

    public MqttRecvThread(MqttInitInfo info) {
        MqttUtils.i("MqttThread", "MqttRecvThread construct");
        mMqttInfo = info;
        statrThread();
    }

    private void statrThread() {
        MqttUtils.i("MqttThread", "MqttRecvThread statrThread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsStopThread) {
                    if (mMqttInfo.get_is_network_error()) {
                        // mMqttInfo.on_idle();
                        mMqttInfo.wait_for_network_ok();
                        continue;
                    } else {
                        if (mMqttInfo.check_connect()) {
                            // connect ok
                            recv();
                        } else {
                            // connect failed, wait for next connect again
                            MqttUtils.e("MqttThread", "mMqttInfo on_idle, connect failed, wait for next connect again");
                            mMqttInfo.on_idle();
                        }
                    }
                }

                mMqttInfo.close_connect();
            }
        }).start();
    }

    public void recv() {
        // recv message
        int recv_message_num = 0;
        while (true) {
            if (mMqttInfo.get_is_socket_error()) {
                MqttUtils.e("MqttThread", "mMqttInfo socket error, need do check");
                break;
            }

            DataInputStream in = mMqttInfo.get_in();
            if (null == in) {
                MqttUtils.e("MqttThread", "mMqttInfo in is null, need do check");
                break;
            }
            
            byte first_byte = 0;
            try {
                first_byte = in.readByte();
            } catch (SocketTimeoutException ti) {
            	logger.error("", ti);
                MqttUtils.i("MqttThread", "recv timeout, IOException [" + ti.toString() + "].");
                break;
            } catch (IOException e1) {
                logger.error("", e1);
                MqttUtils.i("MqttThread", "recv Exception read message first byte failed, IOException [" + e1.toString()
                        + "].");
                if (in == mMqttInfo.get_in())
                {
                	//mMqttInfo.set_is_socket_error();
                }
                else
                {
                	// socket has reconnected, old socket got error, not need do anything
                	MqttUtils.i("MqttThread", "socket has reconnected, old socket got error, not need do anything.");
                }
                break;
            }

            byte type = (byte) ((first_byte >>> 4) & 0x0F);
            if ((type < MqttMessageBase.MESSAGE_TYPE_QUERY_ONLINE) || (type > MqttMessageBase.MESSAGE_TYPE_DISCONNECT)) {
                MqttUtils.e("MqttThread", "recv Exception read message type failed, type is [" + type + "].");

                // break;
            }
            long remLen = 0;
            try {
                remLen = MqttMessageBase.decode_remaining_length(in);
            } catch (IOException e2) {
                logger.error("", e2);
                MqttUtils.e("MqttThread",
                        "recv Exception read message's Remaining Length failed, IOException [" + e2.toString() + "].");
                
                if (in == mMqttInfo.get_in())
                {
                	mMqttInfo.set_is_socket_error();
                }
                else
                {
                	// socket has reconnected, old socket got error, not need do anything
                	MqttUtils.e("MqttThread", "socket has reconnected, old socket got error, not need do anything.");
                }
                
                break;
            }

            byte[] variable_header_and_payload = new byte[(int) remLen];
            try {
                in.readFully(variable_header_and_payload);
            } catch (IOException e3) {
                logger.error("", e3);
                MqttUtils.e("MqttThread", "recv Exception read mqtt's variable header and payload failed, IOException ["
                        + e3.toString() + "].");
                
                if (in == mMqttInfo.get_in())
                {
                	mMqttInfo.set_is_socket_error();
                }
                else
                {
                	// socket has reconnected, old socket got error, not need do anything
                	MqttUtils.e("MqttThread", "socket has reconnected, old socket got error, not need do anything.");
                }
                break;
            }

            recv_message_num++;

            MqttUtils.i("MqttThread", "recved " + recv_message_num + " messages.");
            mMqttInfo.on_heart_beat(); // 只要有消息就认为心跳是好的
            if (type == MqttMessageBase.MESSAGE_TYPE_PINGRESP) {
                // 直接更新消息时间就可以了
                MqttUtils.i("MqttThread", "recved a pingrsp messages, put to process message thread.");
            } else if (type == MqttMessageBase.MESSAGE_TYPE_PUBLISH) {
                MqttUtils.i("MqttThread", "recved a publish messages, put to process message thread.");
                MqttPublishMessage recv_pub_message = new MqttPublishMessage(first_byte);
                recv_pub_message.set_variable_header_and_payload(variable_header_and_payload);
                mMqttInfo.put_recv_message(recv_pub_message);
            } else if (type == MqttMessageBase.MESSAGE_TYPE_DISCONNECT) {
                MqttUtils.i("MqttThread", "recved a disconnect messages, put to process message thread.");
                MqttDisconnect disconnect_message = new MqttDisconnect();
                mMqttInfo.put_recv_message(disconnect_message);
            } else {
                MqttUtils.i("MqttThread", "recved a other messages, put to process message thread.");
                MqttMessageBase recv_other_message = new MqttMessageBase(first_byte);
                recv_other_message.set_variable_header_and_payload(variable_header_and_payload);
                mMqttInfo.put_recv_message(recv_other_message);
            }
            
            // 每次收取一个消息就break出来
            break;
        }
    }

    public void onDestroy() {
    	MqttUtils.i("MqttThread", "recved thread onDestroy.");
        mIsStopThread = true;
    }
}

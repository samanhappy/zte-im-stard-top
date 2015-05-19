package com.zte.im.mqtt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttSendThread {
	
	private final static Logger logger = LoggerFactory.getLogger(MqttSendThread.class);
	
    private MqttInitInfo mMqttInfo = null;
    private Queue<MqttMessageBase> mMessageQueue = new LinkedList<MqttMessageBase>();

    private boolean mIsStopThread = false;

    public MqttSendThread(MqttInitInfo info) {
        MqttUtils.i("MqttThread", "MqttSendThread construct");
        mMqttInfo = info;
        statrThread();
    }

    private void statrThread() {
        MqttUtils.i("MqttThread", "MqttSendThread statrThread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMqttInfo.start_heart_beat_timer();
                while (!mIsStopThread) {
                    send();
                }
                
                MqttUtils.e("MqttThread", "MQTT send thread quit!");
                mMqttInfo.disconnect();
            }
        }).start();
    }

    public void send() {
        // get send message list
        if (mMessageQueue.isEmpty()) {
            mMqttInfo.get_send_message(mMessageQueue);
        }

        if (!mMessageQueue.isEmpty()) {
            boolean is_network_error = mMqttInfo.get_is_network_error();
            if (is_network_error) {
                MqttUtils.e("MqttThread",
                        "network is error status, not need do send action. wait for an idle time and try again");
                mMqttInfo.on_idle();
                // mMqttInfo.wait_for_network_ok();
                is_network_error = mMqttInfo.get_is_network_error();
            }

            if ((mMqttInfo.get_is_socket_error()) && (!is_network_error)) {
                MqttUtils.e("MqttThread", "send_queue socket is error status, not need do send action.");
                // mMqttInfo.on_idle();
                if (mMqttInfo.is_server_error()) {
                    MqttUtils.e("MqttThread", "send_queue socket is server error, send error response.");
                    is_network_error = true;
                } else {
                    mMqttInfo.wait_for_socket_ok();
                }
                // return;
            }

            MqttUtils.i("MqttThread", "get need send message_queue size is [" + mMessageQueue.size() + "]");

            send_queue(mMessageQueue, is_network_error);
        } else {
            // MqttUtils.e("MqttThread", "not mqtt message want to send!");
        }
    }

    public void send_queue(Queue<MqttMessageBase> message_queue, boolean is_network_error) {
        while (true) {
            MqttMessageBase message = message_queue.poll();
            if (null != message) {
                if (is_network_error) {
                    MqttUtils.e("MqttThread",
                            "network is error or server error , can not send message[" + message.toString()
                                    + "] to server!");

                    mMqttInfo.error_response(message);
                    continue;
                }

                // when send message, network error
                if (mMqttInfo.get_is_socket_error()) {
                    MqttUtils.e("MqttThread", "socket is error , not send message to server, break!");
                    break;
                }

                try {
                    byte[] msg = message.encode();
                    if (null == msg) {
                        // try again next time
                        MqttUtils.e("MqttThread", "message encode failed.");
                        mMqttInfo.resend(message);
                        continue;
                    }

                    // check heartbeat
                    /*
                     * if (!mMqttInfo.check_heart_beat())
                     * {
                     * mMqttInfo.set_is_socket_error();
                     * MqttUtils.e("MqttThread",
                     * "check_heart_beat failed.");
                     * 
                     * // try again next time
                     * mMqttInfo.resend(message);
                     * continue;
                     * }
                     */

                    OutputStream out = mMqttInfo.get_out();
                    if (null == out) {
                        MqttUtils.e("MqttThread", "out is null, do resend.");

                        // try again next time
                        mMqttInfo.resend(message);
                    } else {
                        out.write(msg, 0, msg.length);
                        out.flush();
                        MqttUtils.e("MqttThread", "send a mqtt message[" + message.toString() + "] success!");
                    }
                } catch (IOException e1) {
                    mMqttInfo.set_is_socket_error();

                    logger.error("", e1);
                    MqttUtils.e("MqttThread", "send a mqtt message failed, IOException [" + e1.toString() + "].");

                    // try again next time
                    mMqttInfo.resend(message);
                    continue;
                }
            } else {
                MqttUtils.e("MqttThread", "send mqtt message queue finished!");
                break;
            }
        }
    }

    public void onDestroy() {
    	MqttUtils.i("MqttThread", "Send thread onDestroy.");
        mIsStopThread = true;
    }
}

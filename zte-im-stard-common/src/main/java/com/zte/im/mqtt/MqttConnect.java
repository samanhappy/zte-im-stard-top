/* 
 * Copyright (c) 2009, 2012 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 */
package com.zte.im.mqtt;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * An on-the-wire representation of an MQTT CONNECT message.
 */
public class MqttConnect extends MqttMessageBase {
    public static String KEY = "MqttConnect";
    public static final byte CONNECTION_ACCEPTED = 0;
    private String mProtocolName = "MQIsdp";
    private byte mProtocolVersionNumber = 3;
    private byte mUserNameFlag = 0;
    private byte mPasswordFlag = 0;
    private byte mWillRetain = 0;
    private byte mWillQos = 0;
    private byte mWillFlag = 0;
    private byte mCleanSession = 1;
    private int mkeepAliveTimer = 60;
    private String mClientId = "999";
    private String mWillTopic = null;
    private String mWillMessage = null;
    private String mUserName = null;
    private String mPassword = null;

    /**
     * Constructor for an on the wire MQTT connect message
     * 
     * @param info
     * @param data
     * @throws IOException
     * @throws MqttException
     */
    public MqttConnect(String client_id, String user_name, String password, String will_topic, String will_message, int keep_alive_timer) {
        super();
        set_type(MqttMessageBase.MESSAGE_TYPE_CONNECT);
        if (null != client_id) {
            mClientId = client_id;
        }
        
        if (null != user_name) {
        	mUserName = user_name;
        	mUserNameFlag = 1;
        }
        
        if (null != password) {
        	mPassword = password;
        	mPasswordFlag = 1;
        }
        
        if (null != will_topic) {
        	mWillTopic = will_topic;
        }
        
        if (null != will_message) {
        	mWillMessage = will_message;
        	mWillFlag = 1;
        }

        mkeepAliveTimer = keep_alive_timer;
    }

    public void decode_connect_flags(byte flag) {
        mUserNameFlag = (byte) ((flag >>> 7) & 0x01);
        mPasswordFlag = (byte) ((flag >>> 6) & 0x01);
        mWillRetain = (byte) ((flag >>> 5) & 0x01);
        mWillQos = (byte) ((flag >>> 3) & 0x03);
        mWillFlag = (byte) ((flag >>> 2) & 0x01);
        mCleanSession = (byte) ((flag >>> 1) & 0x01);
    }

    public byte encode_connect_flags() {
        return (byte) (((mUserNameFlag << 7) & 0x80) | ((mPasswordFlag << 6) & 0x40) | ((mWillRetain << 5) & 0x20)
                | ((mWillQos << 3) & 0x18) | ((mWillFlag << 2) & 0x04) | ((mCleanSession << 1) & 0x02));
    }

    public String toString() {
        String rc = super.toString();
        rc += " clientId " + mClientId + " keepAliveInterval " + mkeepAliveTimer;
        return rc;
    }

    @Override
    public byte[] encode() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            byte fixed_header = encode_fixed_header();
            dos.write(fixed_header);
            int remain_length = 12 + mClientId.length() + 2;
            if (1 == mWillFlag)
            {
            	remain_length += mWillTopic.length() + 2;
            	remain_length += mWillMessage.length() + 2;
            }
            if (1 == mUserNameFlag)
            {
            	remain_length += mUserName.length() + 2;
            }
            if (1 == mPasswordFlag)
            {
            	remain_length += mPassword.length() + 2;
            }
            set_remaining_Length(remain_length);
            encode_remaining_length(dos);
            encode_string_UTF8(dos, mProtocolName);
            dos.write(mProtocolVersionNumber);
            byte connectFlags = encode_connect_flags();
            dos.write(connectFlags);
            dos.writeShort(mkeepAliveTimer);
            encode_string_UTF8(dos, mClientId);
            if (1 == mWillFlag)
            {
            	encode_string_UTF8(dos, mWillTopic);
            	encode_string_UTF8(dos, mWillMessage);
            }
            if (1 == mUserNameFlag)
            {
            	encode_string_UTF8(dos, mUserName);
            }
            if (1 == mPasswordFlag)
            {
            	encode_string_UTF8(dos, mPassword);
            }
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ioe) {
            MqttUtils.e("MqttThread", "MqttConnect encode IOException[" + ioe.toString() + "].");
            return null;
        }
    }

    /**
     * Returns whether or not this message needs to include a message ID.
     */
    public boolean isMessageIdRequired() {
        return false;
    }

    public String getKey() {
        return new String(KEY);
    }
}

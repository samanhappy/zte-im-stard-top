package com.zte.im.mqtt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttMessageBase {
	
	private final static Logger logger = LoggerFactory.getLogger(MqttMessageBase.class);
	
    private static final String TAG = "MqttMessageBase";
    protected static final String STRING_ENCODING = "UTF-8";
    public static final byte MESSAGE_TYPE_QUERY_ONLINE = 0;
    public static final byte MESSAGE_TYPE_CONNECT = 1;
    public static final byte MESSAGE_TYPE_CONNACK = 2;
    public static final byte MESSAGE_TYPE_PUBLISH = 3;
    public static final byte MESSAGE_TYPE_PUBACK = 4;
    public static final byte MESSAGE_TYPE_PUBREC = 5;
    public static final byte MESSAGE_TYPE_PUBREL = 6;
    public static final byte MESSAGE_TYPE_PUBCOMP = 7;
    public static final byte MESSAGE_TYPE_SUBSCRIBE = 8;
    public static final byte MESSAGE_TYPE_SUBACK = 9;
    public static final byte MESSAGE_TYPE_UNSUBSCRIBE = 10;
    public static final byte MESSAGE_TYPE_UNSUBACK = 11;
    public static final byte MESSAGE_TYPE_PINGREQ = 12;
    public static final byte MESSAGE_TYPE_PINGRESP = 13;
    public static final byte MESSAGE_TYPE_DISCONNECT = 14;
    public static final byte MESSAGE_TYPE_NETWORK_ERROR = 15;

    /** The type of the message (e.g. CONNECT, PUBLISH, PUBACK) */
    private byte mType = MESSAGE_TYPE_QUERY_ONLINE;
    private byte mDUPFlag = 0;
    private byte mQoSLevel = 0;
    private byte mRetain = 0;
    private int mRemainingLength = 0;

    /** The MQTT message ID */
    private int mMsgId = -1;
    private int mRetryTimes = 0;
    private int mIFType = -1;
    private boolean mIsFailedResponse = false;
    private byte[] mVariableHeaderAndPayload = null;

    public MqttMessageBase() {
    }

    public MqttMessageBase(byte first_byte) {
        decode_fixed_header(first_byte);
        mRetryTimes = 0;
    }

    public boolean get_is_failed_response() {
        return mIsFailedResponse;
    }

    public void set_is_failed_response() {
        mIsFailedResponse = true;
    }

    public int get_retry_times() {
        return mRetryTimes;
    }

    public void inc_retry_times() {
        mRetryTimes++;
    }

    public byte[] encode() {
        return null;
    }

    public void decode() {
        byte[] variable = get_variable_header_and_payload();
        if (null == variable) {
            // if error mesage not need decode
            MqttUtils.e("MqttThread", "MqttMessageBase error mesage not need decode.");
            return;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(variable);
        DataInputStream dis = new DataInputStream(bais);
        try {
            set_msgId(dis.readUnsignedShort());
        } catch (IOException e) {
            logger.error("", e);
            MqttUtils.e("MqttThread", "MqttMessageBase decode msg id failed!");
        }
    }

    public String toString() {
        return "Type " + mType + " message id " + mMsgId;
    }

    public void set_variable_header_and_payload(byte[] payload) {
        mVariableHeaderAndPayload = payload;
    }

    public byte[] get_variable_header_and_payload() {
        return mVariableHeaderAndPayload;
    }

    public void set_remaining_Length(int length) {
        mRemainingLength = length;
    }

    public int get_remaining_length() {
        return mRemainingLength;
    }

    public void set_type(byte type) {
        mType = type;
    }

    public byte get_type() {
        return mType;
    }

    public void set_if_type(int type) {
        mIFType = type;
    }

    public int get_if_type() {
        return mIFType;
    }

    public void set_dup_flag(byte flag) {
        mDUPFlag = flag;
    }

    public byte get_dup_flag() {
        return mDUPFlag;
    }

    public void set_qos_level(byte qos) {
        mQoSLevel = qos;
    }

    public byte get_qos_level() {
        return mQoSLevel;
    }

    public void set_retain(byte retain) {
        mRetain = retain;
    }

    public byte get_retain() {
        return mRetain;
    }

    public void set_msgId(int msg_id) {
        mMsgId = msg_id;
    }

    public int get_msgId() {
        return mMsgId;
    }

    public void decode_fixed_header(byte first) {
        mType = (byte) ((first >>> 4) & 0x0F);
        mDUPFlag = (byte) ((first >>> 3) & 0x01);
        mQoSLevel = (byte) ((first >>> 1) & 0x03);
        mRetain = (byte) (first & 0x01);
    }

    public byte encode_fixed_header() {
        return (byte) (((mType << 4) & 0xF0) | ((mDUPFlag << 3) & 0x08) | ((mQoSLevel << 1) & 0x06) | (mRetain & 0x01));
    }

    public void encode_remaining_length(DataOutputStream dos) throws IOException {
        int numBytes = 0;
        long no = mRemainingLength;
        // Encode the remaining length fields in the four bytes
        do {
            byte digit = (byte) (no % 128);
            no = no / 128;
            if (no > 0) {
                digit |= 0x80;
            }
            dos.write(digit);
            numBytes++;
        } while ((no > 0) && (numBytes < 4));
    }

    public static long decode_remaining_length(DataInputStream in) throws IOException {
        byte digit;
        long msgLength = 0;
        int multiplier = 1;
        int count = 0;
        do {
            digit = in.readByte();
            count++;
            msgLength += ((digit & 0x7F) * multiplier);
            multiplier *= 128;
        } while ((digit & 0x80) != 0);

        return msgLength;
    }

    // use readUnsignedShort as decode function
    public byte[] encode_short(int value) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(value);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            MqttUtils.e("MqttThread", "encode_short IOException [" + ex.toString() + "].");
            byte[] tmp = new byte[2];
            tmp[0] = (byte) ((value >>> 8) & 0xFF);
            tmp[1] = (byte) (value & 0xFF);
            return tmp;
        }
    }

    public void encode_string_UTF8(DataOutputStream dos, String stringToEncode) {
        try {
            byte[] encodedString = stringToEncode.getBytes("UTF-8");
            byte byte1 = (byte) ((encodedString.length >>> 8) & 0xFF);
            byte byte2 = (byte) ((encodedString.length >>> 0) & 0xFF);

            dos.write(byte1);
            dos.write(byte2);
            dos.write(encodedString);
        } catch (UnsupportedEncodingException ex) {
            MqttUtils.e("MqttThread", "encode_string_UTF8 UnsupportedEncodingException [" + ex.toString() + "].");
        } catch (IOException ex) {
            MqttUtils.e("MqttThread", "encode_string_UTF8 IOException [" + ex.toString() + "].");
        }
    }

    public String decode_string_UTF8(DataInputStream input) {
        int encodedLength;
        try {
            encodedLength = input.readUnsignedShort();
            byte[] encodedString = new byte[encodedLength];
            input.readFully(encodedString);
            return new String(encodedString, "UTF-8");
        } catch (IOException ex) {
        	MqttUtils.e(TAG, ex.toString());
            return new String("");

        }
    }

}

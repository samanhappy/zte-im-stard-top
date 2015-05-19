package com.zte.im.mqtt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttPublishMessage extends MqttMessageBase
{
	
	private final static Logger logger = LoggerFactory.getLogger(MqttPublishMessage.class);
    private String mTopicName = null;
    private byte[] mPayload = null;
    
    public MqttPublishMessage()
    {
        super();
        set_type(MqttMessageBase.MESSAGE_TYPE_PUBLISH);  
    }
    
    public MqttPublishMessage(byte first)
    {
        super(first);       
    }
    
    public void set_topic_name(String topic)
    {
        mTopicName = topic;
    }
    
    public String get_topic_name()
    {
        return mTopicName;
    }
    public void set_payload(byte[] payload)
    {
        mPayload = payload;
    }
    
    public byte[] get_payload()
    {
        return mPayload;
    }
    
    public byte[] encode()
    {
        MqttUtils.i("MqttThread", "Mqtt publish encode.");
        if (null == mTopicName)
        {
        	MqttUtils.e("MqttThread", "MqttPublishMessage encode mTopicName  is null!");
        	return null;
        }
        
        if (null == mPayload)
        {
        	MqttUtils.e("MqttThread", "MqttPublishMessage encode mPayload  is null!");
        	return null;
        }
        
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            byte fixed_header = encode_fixed_header();
            dos.write(fixed_header);
            if (get_qos_level() > 0)
            {
                set_remaining_Length(2 + mTopicName.length() + 2 + mPayload.length);
            }
            else
            {
                set_remaining_Length(2 + mTopicName.length() + mPayload.length);
            }
            encode_remaining_length(dos);            
            encode_string_UTF8(dos, mTopicName);
            if (get_qos_level() > 0)
            {
                dos.writeShort(get_msgId());
            }
            dos.write(mPayload, 0, mPayload.length);            
            dos.flush();
            return baos.toByteArray();
        }
        catch (IOException ioe)
        {
            MqttUtils.e("MqttThread", "MqttPublishMessage encode IOException[" + ioe.toString() + "].");
            return null;
        }
    }
    
    public void decode()
    {
        try
        {
            byte[] variable = get_variable_header_and_payload();
            ByteArrayInputStream bais = new ByteArrayInputStream(variable);
            DataInputStream dis = new DataInputStream(bais);            
            mTopicName = decode_string_UTF8(dis);
            int var_length = mTopicName.length() + 2;
            if (get_qos_level() > 0)
            {
                set_msgId(dis.readUnsignedShort());
                var_length += 2;
            }
            mPayload = new byte[variable.length - var_length];        
            dis.readFully(mPayload);
            dis.close();
        }
        catch (IOException e)
        {
            logger.error("", e);
            MqttUtils.e("MqttThread", "MqttPublishMessage decode IOException [" + e.toString() + "].");
        }
    }
}

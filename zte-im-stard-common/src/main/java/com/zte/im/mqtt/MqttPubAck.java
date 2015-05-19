package com.zte.im.mqtt;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MqttPubAck extends MqttMessageBase {
	public MqttPubAck() {
        super();
        set_type(MqttMessageBase.MESSAGE_TYPE_PUBACK);
    }
	
	/**
     * Returns <code>false</code> as message IDs are not required for MQTT
     * PINGREQ messages.
     */
    public boolean isMessageIdRequired() {
        return true;
    }
	
    public String getKey() {
        return new String("PubAck");
    }
    
	public byte[] encode()
    {
        MqttUtils.i("MqttThread", "Mqtt publish Ack encode.");
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            byte fixed_header = encode_fixed_header();
            dos.write(fixed_header);
            set_remaining_Length(2);
            encode_remaining_length(dos);
            dos.writeShort(get_msgId());          
            dos.flush();
            return baos.toByteArray();
        }
        catch (IOException ioe)
        {
            MqttUtils.e("MqttThread", "MqttConnect encode IOException[" + ioe.toString() + "].");
            return null;
        }
    }	
}

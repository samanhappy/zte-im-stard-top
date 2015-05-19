package com.zte.im.mqtt;

public class MqttDisconnect extends MqttMessageBase {
    private static byte[] Disconnectdata = new byte[2];

    public MqttDisconnect() {
        super();
        set_type(MqttMessageBase.MESSAGE_TYPE_DISCONNECT);
        Disconnectdata[0] = (byte) 0xE0;
        Disconnectdata[1] = (byte) 0x00;
    }

    /**
     * Returns <code>false</code> as message IDs are not required for MQTT
     * PINGREQ messages.
     */
    public boolean isMessageIdRequired() {
        return false;
    }

    public byte[] encode() {
        MqttUtils.i("MqttThread", "MqttDisconnect encode .");
        return Disconnectdata;
    }

    public String getKey() {
        return new String("Disconnect");
    }
}

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

/**
 * An on-the-wire representation of an MQTT PINGREQ message.
 */
public class MqttPingReq extends MqttMessageBase {
    private static byte[] Pingdata = new byte[2];

    public MqttPingReq() {
        super();
        set_type(MqttMessageBase.MESSAGE_TYPE_PINGREQ);
    }

    /**
     * Returns <code>false</code> as message IDs are not required for MQTT
     * PINGREQ messages.
     */
    public boolean isMessageIdRequired() {
        return false;
    }

    public byte[] encode() {
        MqttUtils.i("MqttThread", "MqttPingReq encode .");
        Pingdata[0] = (byte) 0xC0;
        Pingdata[1] = (byte) 0x00;
        return Pingdata;
        /*
         * try
         * {
         * ByteArrayOutputStream baos = new ByteArrayOutputStream();
         * DataOutputStream dos = new DataOutputStream(baos);
         * byte fixed_header = encode_fixed_header();
         * dos.write(fixed_header);
         * set_remaining_Length(0);
         * encode_remaining_length(dos);
         * dos.flush();
         * return baos.toByteArray();
         * }
         * catch (IOException ioe)
         * {
         * MqttUtils.e("MqttThread", "MqttPingReq encode IOException[" + ioe.toString() + "].");
         * return null;
         * }
         */
    }

    public String getKey() {
        return new String("Ping");
    }
}

package org.eclipse.paho.sample.mqttv3app;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.PushBean;
import com.zte.im.mqtt.MqttInitInfo;
import com.zte.im.mqtt.MqttProcessMessageThread;
import com.zte.im.mqtt.MqttPublishMessage;
import com.zte.im.mqtt.OnMqttSeviceIF;
import com.zte.im.util.SystemConfig;

public class MqttPublisher {

	private final static Logger logger = LoggerFactory.getLogger(MqttPublisher.class);

	private static int qos = 1;
	private static int heart_beat_interval = 60;
	private static String device_id = "server_mqtt";
	private static int message_id = new Random().nextInt(65532);

	private static MqttProcessMessageThread process;

	public void init(String clientId) {
		try {
			String[] strs = SystemConfig.mqtt_broker.replace("tcp://", "").split(":");
			MqttInitInfo info = new MqttInitInfo(strs[0], Integer.valueOf(strs[1]), qos, heart_beat_interval, clientId,
					device_id);
			process = MqttProcessMessageThread.getInstance(info, new OnMqttSeviceIF() {
				@Override
				public void onSuccess(int paramInt1, int paramInt2) {
					logger.info("onSuccess");
				}

				@Override
				public void onFailure(int paramInt1, int paramInt2) {
					logger.info("onFailure");
				}

				@Override
				public void onDisconnect() {
					logger.info("onDisconnect");
				}

				@Override
				public void messageArrived(MqttPublishMessage paramMqttPublishMessage) {
					logger.info("messageArrived");
				}
			});
		} catch (Exception e) {
			logger.error("", e);
			logger.info("Unable to set up client: " + e.toString());
		}

	}

	public static void publish(String topicName, int qos, byte[] payload) {
		MqttPublishMessage message = new MqttPublishMessage();
		message.set_topic_name(topicName);
		message.set_qos_level((byte) qos);
		message.set_payload(payload);
		message.set_msgId(message_id);
		process.put_message(message);
		logger.info("put publish message:{} to topic:{}", new String(message.get_payload()), topicName);
	}

	public static void publish(PushBean bean) {
		if (bean.getSendtime() == 0) {
			bean.setSendtime(new Date().getTime());
		}
		byte[] payload = getGsonStringWithLength(JSON.toJSONString(bean));
		String topicName = bean.getTarget() != null ? bean.getTarget() : "g0";
		MqttPublishMessage message = new MqttPublishMessage();
		message.set_topic_name(topicName);
		message.set_qos_level((byte) qos);
		message.set_payload(payload);
		message.set_msgId(message_id);
		logger.info("put publish message:{} to topic:{}", new String(message.get_payload()), topicName);
		process.put_message(message);
	}

	public static void publish(List<PushBean> list) {
		for (PushBean bean : list) {
			if (bean.getSendtime() == 0) {
				bean.setSendtime(new Date().getTime());
			}
			byte[] payload = getGsonStringWithLength(JSON.toJSONString(bean));
			String topicName = bean.getTarget() != null ? bean.getTarget() : "g0";
			MqttPublishMessage message = new MqttPublishMessage();
			message.set_topic_name(topicName);
			message.set_qos_level((byte) qos);
			message.set_payload(payload);
			message.set_msgId(message_id);
			logger.info("put publish message:{} to topic:{}", new String(message.get_payload()), topicName);
			process.put_message(message);
		}
	}

	public static byte[] getGsonStringWithLength(String strJson) {
		byte[] jsonBytes = strJson.getBytes();
		String jsonlen = String.valueOf(jsonBytes.length);
		String head = jsonlen + "#";
		byte[] data = new byte[head.length() + jsonBytes.length];
		byte[] headbyte = head.getBytes();
		System.arraycopy(headbyte, 0, data, 0, headbyte.length);
		System.arraycopy(jsonBytes, 0, data, headbyte.length, jsonBytes.length);
		return data;
	}

	public static void main(String[] args) {
		new MqttPublisher().init("20");
		PushBean bean = new PushBean();
		bean.setKeyid("1000031");
		bean.setModeltype("0");
		bean.setMsg("李孔泽创建了群组");
		bean.setSessionid("30391");
		bean.setSessionname("未命名");
		bean.setTarget("1000037");
		bean.setType("0");
		bean.setUsername("李孔泽");
		MqttPublisher.publish(bean);

		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			logger.error("", e);
		}

		MqttPublisher.publish(bean);
	}

}

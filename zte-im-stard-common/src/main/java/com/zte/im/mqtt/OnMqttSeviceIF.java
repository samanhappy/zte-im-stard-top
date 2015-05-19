package com.zte.im.mqtt;


public interface OnMqttSeviceIF {
   
    //滚动的回调接口
    public void messageArrived(MqttPublishMessage message);

    public void onSuccess(int type, int msgId);

    public void onFailure(int type, int msgId);
    
    public void onDisconnect();
}

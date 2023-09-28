package com.example.firebasechat;

public class MessageModel {
    String uId;
    String message;
    String messageId;
    String msgType;
    String lastMsg;
    String tmStamp;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    String userType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    Long timestamp;

    public MessageModel(String uId, String message, String msgType, String userType) {
        this.uId = uId;
        this.message = message;
        this.msgType = msgType;
        this.userType = userType;
    }

    public MessageModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel() {
    }

    public String getuId() {
        return uId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String  getTmStamp() {
        return tmStamp;
    }

    public void setTmStamp(String tmStamp) {
        this.tmStamp = tmStamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

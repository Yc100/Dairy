package com.yc.dto;

import java.util.Map;

public class ChatMsgDto {
    private String date;//消息日期
    private String textMsg;//消息内容
    private boolean mine;
    private String name;//当前消息的发送人名称
    private String img;//当前消息的发送人头像
    private Map<String,Object> text;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }

    public Map<String, Object> getText() {
        return text;
    }

    public void setText(Map<String, Object> text) {
        this.text = text;
    }
}

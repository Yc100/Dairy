package com.yc.dto;

import java.util.List;

public class ChatFriendDto {
    private String img;//好友头像
    private String name;//好友名字
    private Long friendId;//好友id


    private List<ChatMsgDto> chatMsgDtoList;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public List<ChatMsgDto> getChatMsgDtoList() {
        return chatMsgDtoList;
    }

    public void setChatMsgDtoList(List<ChatMsgDto> chatMsgDtoList) {
        this.chatMsgDtoList = chatMsgDtoList;
    }
}

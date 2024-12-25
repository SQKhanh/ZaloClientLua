/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo.model.dto;

import lombok.Getter;
import org.json.JSONObject;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
@Getter
public class MessageFromListen {

    private final boolean isSelf;
    private final JSONObject data;

    private final int typeMessage;
    private final String threadId;

    /**
     *
     * @param typeMessage: 0 tin nhắn cá nhân, 1 tin nhắn nhóm
     * @param data
     */
    public MessageFromListen(int typeMessage, JSONObject data) {
        switch (typeMessage) {
            case 0:
            case 1:
                break;
            default:
                throw new RuntimeException("Invalid TypeMessage");
        }
        this.typeMessage = typeMessage;
        this.isSelf = data.optString("uidFrom").equals("0");
        this.data = data;

        this.threadId = typeMessage == 0
                ? getUidFrom().equals("0") ? data.getString("idTo") : data.getString("uidFrom")
                : data.getString("idTo");
    }

    public final Object getContent() {
        return data.opt("content");
    }

    public final String getDName() {
        return data.optString("dName", null);
    }

    public final String getUidFrom() {
        return data.optString("uidFrom", null);
    }

}

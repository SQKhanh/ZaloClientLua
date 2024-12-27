/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo.model.dto.msgType;

import com.khanhdz.config.ZaloConfigHelper;
import lombok.Getter;
import org.json.JSONObject;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
@Getter
public class WebChat {

    public static final int TYPE_MESSAGE_DIRECT = 0;
    public static final int TYPE_MESSAGE_GROUP = 1;

    private final boolean isSelf;
    private final JSONObject data;

    private final int typeMessage;
    private final String threadId;

    private final boolean isFromMaster;

    /**
     *
     * @param typeMessage: 0 tin nhắn cá nhân, 1 tin nhắn nhóm
     * @param data
     */
    public WebChat(int typeMessage, JSONObject data) {
        switch (typeMessage) {
            case TYPE_MESSAGE_DIRECT:
            case TYPE_MESSAGE_GROUP:
                break;
            default:
                throw new RuntimeException("Unknow TypeMessage: " + typeMessage);
        }
        this.typeMessage = typeMessage;
        this.isSelf = data.optString("uidFrom").equals("0");
        this.data = data;

        this.threadId = typeMessage == 0
                ? getUidFrom().equals("0") ? data.getString("idTo") : data.getString("uidFrom")
                : data.getString("idTo");

        this.isFromMaster = ZaloConfigHelper.isMessageFromMaster(data.optString("uidFrom", ""));
    }

    public final String getContent() {
        return data.optString("content", "");
    }

    public final String getDName() {
        return data.optString("dName", "");
    }

    public final String getUidFrom() {
        return data.optString("uidFrom", "");
    }

}

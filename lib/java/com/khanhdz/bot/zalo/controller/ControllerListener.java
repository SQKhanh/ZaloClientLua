/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo.controller;

import com.khanhdz.MainConfig;
import com.khanhdz.bot.zalo.ZaloLua;
import com.khanhdz.bot.zalo.model.dto.msgType.WebChat;
import com.khanhdz_util.Logger;
import java.util.Arrays;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
public class ControllerListener {

    public static final ControllerListener Instance = new ControllerListener();

    private ControllerListener() {
    }

    private String cipherKey;

    public void process(WebSocketClient webSocketClient, JSONObject parsed, final int[] header) {
        try {
            final int n = header[0];
            final int cmd = header[1];
            final int s = header[2];
            switch (cmd) {
                case 1 -> {
                    if (n == 1 && s == 1 && parsed.has("key")) {
                        this.cipherKey = parsed.getString("key");
                        Logger.DebugLogic("set new cipherKey: " + parsed.toString(4));
//                    JSONObject parsedData = ZaloNetSender.decodeEventData(parsed, ZaloWebSocketClient.this.cipherKey).getJSONObject("data");
//                    Logger.DebugLogic("parsedData: " + parsedData.toString(4));
                    }
                }
                case 3000 -> {
                    if (n == 1 && s == 0) {
                        Logger.DebugLogic("Another connection is opened, closing this one");
                        if (webSocketClient.getReadyState() != ReadyState.CLOSED) {
                            webSocketClient.close();
                        }
                    }
                }
                case 501 -> {
                    // tin nhắn riêng
                    if (n == 1 && s == 0) {
                        JSONObject parsedData = DecodeEventData.decodeEventData(parsed, this.cipherKey).optJSONObject("data", null);
                        JSONArray msgs = parsedData.optJSONArray("msgs", null);
                        if (msgs == null) {
                            Logger.DebugLogic("msgs is null");
                            break;
                        }
                        for (int i = 0; i < msgs.length(); i++) {
                            JSONObject msg = msgs.optJSONObject(i);
                            if (msg == null) {
                                continue;
                            }
                            // nếu là tin nhắn của bản thân
                            if (msg.optString("uidFrom").equals("0")) {
                                if (MainConfig.Instance.getZalo().isSelfListen()) {
                                    ZaloLua.Instance.callLuaAPI(ZaloLua.API.EVENT_MESSAGE_DIRECT_WEBCHAT_SELF, new WebChat(WebChat.TYPE_MESSAGE_DIRECT, msg));
                                }
                            } else {
                                switch (msg.getString("msgType")) {
                                    case "chat.undo":
//                                    ZaloWebSocketClient.this.callBackEventMessageChatUndo.accept(new MessageFromListen(0, msg));
                                        break;
                                    case "webchat":
                                        ZaloLua.Instance.callLuaAPI(ZaloLua.API.EVENT_MESSAGE_DIRECT_WEBCHAT, new WebChat(WebChat.TYPE_MESSAGE_DIRECT, msg));
                                        break;
                                    default:
                                        Logger.DebugLogic("unknow msgType: " + msg.toString(4));
                                        break;
                                }
                            }
                        }
                    } else {
                        Logger.DebugLogic("Unknow header: " + Arrays.toString(header));
                    }
                }
                case 521 -> {
                    // tin nhắn nhóm
                    if (n == 1 && s == 0) {
                        JSONObject parsedData = DecodeEventData.decodeEventData(parsed, this.cipherKey).getJSONObject("data");
                        JSONArray msgs = parsedData.optJSONArray("groupMsgs", null);
                        if (msgs == null) {
                            Logger.DebugLogic("msgs is null");
                            break;
                        }
//                                System.out.println("msgs: " + msgs.toString(4));

                        for (int i = 0; i < msgs.length(); i++) {
                            JSONObject msg = msgs.optJSONObject(i);
                            if (msg == null) {
                                continue;
                            }

                            // nếu là tin nhắn của bản thân
                            if (msg.optString("uidFrom").equals("0")) {
                                if (MainConfig.Instance.getZalo().isSelfListen()) {
                                    ZaloLua.Instance.callLuaAPI(ZaloLua.API.EVENT_MESSAGE_GROUP_WEBCHAT_SELF, new WebChat(WebChat.TYPE_MESSAGE_GROUP, msg));
                                }
                            } else {
                                switch (msg.getString("msgType")) {
//                                    case "chat.delete":
                                    ////                                    ZaloWebSocketClient.this.callBackEventMessageChatDelete.accept(new MessageFromListen(1, msg));
//                                        break;
//                                    case "chat.undo": // thu hồi
////                                    ZaloWebSocketClient.this.callBackEventMessageChatUndo.accept(new MessageFromListen(1, msg));
//                                        break;
                                    case "webchat":
                                        ZaloLua.Instance.callLuaAPI(ZaloLua.API.EVENT_MESSAGE_GROUP_WEBCHAT, new WebChat(WebChat.TYPE_MESSAGE_GROUP, msg));
                                        break;
                                    default:
                                        Logger.DebugLogic("unknow msgType: " + msg.toString(4));
                                        break;
                                }

                            }
                        }
                    } else {
                        Logger.DebugLogic("Unknow header: " + Arrays.toString(header));
                    }
                }
                case 612 -> {
                    // thả cảm xúc
                    if (n == 1 && s == 0) {
                        JSONObject parsedData = DecodeEventData.decodeEventData(parsed, this.cipherKey);
                        Logger.DebugLogic("""
                                                          Thả cảm xúc
                                                          data:%s""".formatted(parsedData.toString(4)));
                    } else {

                    }
                }
                default -> {
                    if (parsed.getInt("encrypt") == 2) {
                        Logger.DebugLogic("""
                                                          Unknow Message
                                                          Header: %s
                                                          Data: %s"""
                                .formatted("n: %s, cmd: %s, s: %s".formatted(n, cmd, s), parsed.toString(4)));
                    }
                }
            }
        } catch (Exception e) {
            Logger.error("""
                        Controller Listener
                         header: %s
                        data: %s""".formatted(Arrays.toString(header), parsed.toString(4)), e);
        }
    }
}

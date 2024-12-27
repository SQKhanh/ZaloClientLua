/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo;

import com.khanhdz.bot.zalo.model.dto.msgType.WebChat;
import com.khanhdz.bot.zalo.security.AESCipher;
import com.khanhdz_core.ObjectKhanhdz;
import com.khanhdz_util.LogFatal;
import org.json.JSONObject;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
public final class ZaloAPI extends ObjectKhanhdz {

    public static final ZaloAPI Instance = new ZaloAPI();

    private final String urlChatDirect;
    private final String urlChatGroup;

    private final String urlQuote;
    private final String urlQuoteGroup;

    private ZaloAPI() {
        var zpw_service_map_v3 = ZaloNetSender.Instance.getZpw_service_map_v3();

        String urlChatDirect = null;
        try {
            urlChatDirect = createURL("/api/message/sms", "chat", zpw_service_map_v3);
        } catch (Exception e) {
            LogFatal.log("create urlChatDicrect", e, null);
        }
        this.urlChatDirect = urlChatDirect;

        String urlChatGroup = null;
        try {
            urlChatGroup = createURL("/api/group/sendmsg", "group", zpw_service_map_v3);
        } catch (Exception e) {
            LogFatal.log("create urlChatGroup", e, null);
        }
        this.urlChatGroup = urlChatGroup;

        String urlQuote = null;
        try {
            urlQuote = createURL("/api/message/quote", "chat", zpw_service_map_v3);
        } catch (Exception e) {
            LogFatal.log("create urlQuote", e, null);
        }
        this.urlQuote = urlQuote;

        String urlQuoteGroup = null;
        try {
            urlQuoteGroup = createURL("/api/group/quote", "group", zpw_service_map_v3);
        } catch (Exception e) {
            LogFatal.log("create urlQuoteGroup", e, null);
        }
        this.urlQuoteGroup = urlQuoteGroup;
    }

    private static String createURL(String endpoint, String type, JSONObject mapURL) throws Exception {
        return ZaloNetSender.makeURL(
                mapURL.getJSONArray(type).get(0).toString() + endpoint,
                new JSONObject()
                        .put("zpw_ver", ZaloData.Instance.getAPI_VERSION())
                        .put("zpw_type", ZaloData.Instance.getAPI_TYPE())
                        .put("nretry", 0)
        );
    }

    protected final void init() {
        System.out.println(Instance.getClass().getSimpleName() + " init done");
    }

    /**
     *
     * @param content
     * @param toId
     * @param type - 0: tin nhắn cá nhân, 1: tin nhắn trong nhóm
     *
     * @return JSONObject: có thể null nếu gọi không thành công
     *
     */
    public JSONObject sendMessageWebChat(String content, String toId, int type) {
        JSONObject payload;
        String url;
        switch (type) {
            case 0:
                payload = new JSONObject();
                payload.put("toid", toId);
                payload.put("clientId", System.currentTimeMillis());
                payload.put("imei", ZaloData.Instance.getImei());
                payload.put("message", content);
                payload.put("ttl", 0);
                url = this.urlChatDirect;
                break;
            case 1:
                payload = new JSONObject();
                payload.put("clientId", System.currentTimeMillis());
                payload.put("visibility", 0);
                payload.put("grid", toId);
                payload.put("imei", ZaloData.Instance.getImei());
                payload.put("message", content);
                payload.put("ttl", 0);
                url = this.urlChatGroup;
                break;
            default:
                throw new RuntimeException("Invalid type message");
        }

        try {
            String encryptedPayload = ZaloNetSender.Instance.encryptedPayload(payload);

            return ZaloNetSender.Instance.callApi(url, new JSONObject()
                    .put("method", "POST")
                    .put("body",
                            new JSONObject()
                                    .put("params", encryptedPayload)
                    ));
        } catch (Exception e) {
            this.logError("""
                          sendMessage()
                          content: %s
                          toId: %s
                          type: %s"""
                    .formatted(content, toId, type),
                    e);
        }
        return null;
    }

    public JSONObject replyMessage(String reply, WebChat message) {
        return replyMessage(reply, false, message);
    }

    public JSONObject replyMessageMention(String reply, WebChat message) {
        return replyMessage(reply, true, message);
    }

    /**
     *
     * @param reply
     * @param mention - chỉ khả dụng khi reply trong group
     * @param message
     * @return
     * @throws Exception
     */
    private JSONObject replyMessage(String reply, boolean mention, WebChat message) {
        JSONObject payload;
        String url;

        switch (message.getTypeMessage()) {
            case 0: // cá nhân
                switch (message.getData().getString("msgType")) {
                    case "webchat":
                        payload = new JSONObject();
                        payload.put("toid", message.getThreadId());
                        payload.put("qmsgId", message.getData().get("msgId"));
                        payload.put("clientId", System.currentTimeMillis());
                        payload.put("qmsg", message.getContent());
                        payload.put("qmsgOwner", message.getData().get("uidFrom"));
                        payload.put("qmsgType", ZaloNetSender.getClientMessageType(message.getData().getString("msgType")));
                        payload.put("qmsgTs", message.getData().get("ts"));
                        payload.put("imei", ZaloData.Instance.getImei());
                        payload.put("qmsgTTL", message.getData().get("ttl"));
                        payload.put("message", reply);
                        payload.put("qmsgCliId", message.getData().get("cliMsgId"));
                        payload.put("ttl", 0);
                        break;
                    default:
                        throw new RuntimeException("This msgType<" + message.getData().getString("msgType") + "> is not supported yet.");
                }
                url = this.urlQuote;
                break;
            case 1: // nhóm

                payload = new JSONObject();
                payload.put("qmsgId", message.getThreadId());
                payload.put("clientId", System.currentTimeMillis());
                payload.put("visibility", 0);

                switch (message.getData().getString("msgType")) {
                    case "webchat":
                        payload.put("qmsg", message.getContent());
                        break;
                    default:
                        payload.put("qmsg", "");
                        break;
                }
                payload.put("qmsgTs", message.getData().get("ts"));

                if (mention) {
                    payload.put("mentionInfo", "[{\"pos\":%s,\"len\":%s,\"uid\":\"%s\",\"type\":%s}]".
                            formatted(0, message.getDName().length() + 1, message.getUidFrom(), 0));
//                    payload.put("mentionInfo", "[{\"pos\":0,\"len\":3,\"uid\":\"425727889141607613\",\"type\":0}]");

                    reply = "@%s %s".formatted(message.getDName(), reply);
                    payload.put("message", reply);
                } else {
                    payload.put("message", reply);
                }

                payload.put("qmsgCliId", message.getData().get("cliMsgId"));
                payload.put("ttl", 0);
//                payload.put("qmsgAttach", "{\"mentions\":[{\"uid\":\"7254063746036403966\",\"pos\":0,\"len\":14,\"type\":0}],\"properties\":{\"color\":-1,\"size\":-1,\"type\":-1,\"subType\":0,\"ext\":\"{\\\"sSrcType\\\":-1,\\\"sSrcStr\\\":\\\"\\\",\\\"msg_warning_type\\\":0,\\\"emoji\\\":{\\\"content\\\":0,\\\"num\\\":0,\\\"uniq\\\":0,\\\"first\\\":\\\"\\\",\\\"last\\\":\\\"\\\",\\\"most\\\":\\\"\\\",\\\"text\\\":1}}\"}}");
                payload.put("qmsgAttach", ZaloNetSender.prepareQMSGAttach(message).toString());
                payload.put("grid", message.getThreadId());
                payload.put("qmsgOwner", message.getData().get("uidFrom"));
                payload.put("qmsgType", ZaloNetSender.getClientMessageType(message.getData().getString("msgType")));
                payload.put("qmsgTTL", message.getData().get("ttl"));

                url = this.urlQuoteGroup;
                break;
            default:
                throw new RuntimeException("Invalid type message");
        }
        try {
            String encryptedPayload = AESCipher.encodeAES(ZaloNetSender.Instance.getSecretKey(), payload.toString());

            return ZaloNetSender.Instance.callApi(url, new JSONObject()
                    .put("method", "POST")
                    .put("body",
                            new JSONObject()
                                    .put("params", encryptedPayload)
                    ));

        } catch (Exception e) {
            this.logError("""
                          replyMessage()
                          reply: %s
                          mention: %s
                          message: %s"""
                    .formatted(reply, mention, message),
                    e);
        }
        return null;
    }
}

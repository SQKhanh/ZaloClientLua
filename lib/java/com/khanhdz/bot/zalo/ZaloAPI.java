/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo;

import com.khanhdz_core.ObjectKhanhdz;
import com.khanhdz_util.LogFatal;
import com.khanhdz_util.Logger;
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
            urlQuoteGroup = createURL("/api/message/quote", "group", zpw_service_map_v3);
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
    public JSONObject sendMessage(String content, String toId, int type) {
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

}

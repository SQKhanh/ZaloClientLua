/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
public final class Zalo {

    public static final Zalo Instance = new Zalo();

    private Zalo() {
        ZaloData.Instance.init();
        ZaloLua.Instance.init();
        ZaloNetSender.Instance.init();
        ZaloNetListener.Instance.init();
        ZaloAPI.Instance.init();
    }
    private boolean isStart;

    public final void start() {
        if (this.isStart) {
            return;
        }
        this.isStart = true;

        System.out.println(Instance.getClass().getSimpleName() + " start listener");

        ZaloLua.Instance.callLuaAPI(ZaloLua.API.LISTENER_ON_BEFORE_OPEN);

        ZaloNetListener.Instance.getWebSocketClient().connect();
    }

    public final static void systemExit(int code) {
        System.exit(code);
    }

    public final void reloadLua() {
        ZaloLua.Instance.loadAllLua(false);
    }

}

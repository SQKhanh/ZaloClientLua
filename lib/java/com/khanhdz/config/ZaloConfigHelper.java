/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.config;

import com.khanhdz.MainConfig;
import java.util.List;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
public class ZaloConfigHelper {

    public static boolean isMessageFromMaster(String idFrom) {
        return MainConfig.Instance.getZalo().getIdMaster().equals(idFrom);
    }

    public static boolean isGroupActive(String groupId) {
        return MainConfig.Instance.getZalo().getIdGroupActive().contains(groupId);
    }

    public static List<String> getListGroupActive() {
        return MainConfig.Instance.getZalo().getIdGroupActive();
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.config;

import java.util.List;
import lombok.Getter;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
@Getter
public class ZaloConfig {

    private String pathDataAccount;

    private boolean selfListen;

    private int apiVersion;

    private int apiType;

    private boolean listenerAutoReconnectOnFailurePingPong;

    private String idMaster;

    private List<String> idGroupActive;

}

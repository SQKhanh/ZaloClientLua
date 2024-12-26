/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo.model.dto;

import lombok.Getter;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
@Getter
public class Mention {

    /**
     * mention position
     */
    private int pos;
    /**
     * id of the mentioned user
     */
    private String uid;
    /**
     * length of the mention
     */
    private int len;

    public Mention(int pos, String uid, int len) {
        this.pos = pos;
        this.uid = uid;
        this.len = len;
    }

}

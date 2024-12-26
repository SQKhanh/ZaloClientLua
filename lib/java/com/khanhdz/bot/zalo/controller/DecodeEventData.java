/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khanhdz.bot.zalo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
 */
public class DecodeEventData {

    //encrypt:2 thì dùng cái này
    public static JSONObject decodeEventData(JSONObject parsed, String cipherKey) throws Exception {
        String base64Data = parsed.getString("data");
        String result = decodeEventData(base64Data, cipherKey);
        JSONObject object = new JSONObject(result);
        return object;
    }

    private static final int TAG_LENGTH_BIT = 128; // Bit length of the authentication tag
    private static final int NONCE_LENGTH_BYTE = 16; // Length of nonce in bytes
    private static final int ADDITIONAL_DATA_LENGTH = 16; // Length of additional data in bytes

    // Chuyển đổi chuỗi Base64 thành mảng byte
    private static byte[] decodeBase64ToBuffer(String data) {
        return Base64.getDecoder().decode(data);
    }

    // Chuyển đổi mảng byte thành chuỗi
    private static String decodeUint8Array(byte[] data) throws IOException {
        return new String(data, StandardCharsets.UTF_8);
    }

    // Giải nén dữ liệu Gzip
    private static byte[] decompressGzip(byte[] compressedData) throws IOException {
        try (InputStream byteStream = new ByteArrayInputStream(compressedData); GZIPInputStream gzipStream = new GZIPInputStream(byteStream)) {
            return gzipStream.readAllBytes();
        }
    }

    public static String decodeEventData(String base64Data, String cipherKeyBase64) throws Exception {
        if (cipherKeyBase64 == null || base64Data == null) {
            return null;
        }

        byte[] decodedEventDataBuffer = decodeBase64ToBuffer(base64Data);

        if (decodedEventDataBuffer.length >= NONCE_LENGTH_BYTE + ADDITIONAL_DATA_LENGTH) {
            byte[] nonce = new byte[NONCE_LENGTH_BYTE];
            byte[] additionalData = new byte[ADDITIONAL_DATA_LENGTH];
            byte[] dataSource = new byte[decodedEventDataBuffer.length - NONCE_LENGTH_BYTE - ADDITIONAL_DATA_LENGTH];

            System.arraycopy(decodedEventDataBuffer, 0, nonce, 0, NONCE_LENGTH_BYTE);
            System.arraycopy(decodedEventDataBuffer, NONCE_LENGTH_BYTE, additionalData, 0, ADDITIONAL_DATA_LENGTH);
            System.arraycopy(decodedEventDataBuffer, NONCE_LENGTH_BYTE + ADDITIONAL_DATA_LENGTH, dataSource, 0, dataSource.length);

//            System.out.println("nonce: " + Base64.getEncoder().encodeToString(nonce));
//            System.out.println("additionalData: " + Base64.getEncoder().encodeToString(additionalData));
//            System.out.println("dataSource: " + Base64.getEncoder().encodeToString(dataSource));
            byte[] bufferKey = decodeBase64ToBuffer(cipherKeyBase64);
            SecretKeySpec keySpec = new SecretKeySpec(bufferKey, "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
            cipher.updateAAD(additionalData);

            byte[] decryptedData = cipher.doFinal(dataSource);

//            System.out.println("decryptedData: " + Base64.getEncoder().encodeToString(decryptedData));
            // Giải nén dữ liệu Gzip
            byte[] decompressedData = decompressGzip(decryptedData);
            String decodedData = decodeUint8Array(decompressedData);

            if (decodedData != null) {
                return decodedData;
            }
        }
        return null;
    }
}

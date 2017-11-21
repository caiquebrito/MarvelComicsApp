package com.marvelcomics.brito.data.datasource.remote.utils;

import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthHashGenerator {
    public String generateHash(String timestamp, String publicKey, String privateKey)
            throws MarvelApiException {
        try {
            String value = timestamp + privateKey + publicKey;
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(value.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                md5.append(Integer.toHexString((md5Byte & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        }catch (NoSuchAlgorithmException e) {
            throw new MarvelApiException("cannot generate the api key", e);
        }
    }
}

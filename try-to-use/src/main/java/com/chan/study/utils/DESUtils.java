package com.chan.study.utils;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DESede对称加密算法
 */
public class DESUtils {

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "DES";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";


    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM_CBC = "DES/CBC/PKCS5Padding";


    public static final String CHARSET = "UTF-8";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     *
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     *
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k, new SecureRandom());
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     *
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k, new SecureRandom());
        return cipher.doFinal(data);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     *
     * @return byte[] 加密后的数据
     */
    public static byte[] encryptCBC(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        IvParameterSpec param = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, k, param);
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     *
     * @return byte[] 解密后的数据
     */
    public static byte[] decryptCBC(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        IvParameterSpec param = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, k, param);
        return cipher.doFinal(data);
    }

    public static String encrypt(String data, String key) throws Exception {
        return Base64.encodeBase64URLSafeString(encrypt(data.getBytes(), key.getBytes()));
    }

    public static String decrypt(String data, String key) throws Exception {
        return new String(decrypt(Base64.decodeBase64(data), key.getBytes()));
    }

    public static String encryptCBC(String data, String key) throws Exception {
        return encryptCBC(data, key, CHARSET);
    }

    public static String encryptCBC(String data, String key, String charset) throws Exception {
        return Base64.encodeBase64URLSafeString(encryptCBC(data.getBytes(charset), key.getBytes(charset)));
    }

    public static String decryptCBC(String data, String key) throws Exception {
        return decryptCBC(data, key, CHARSET);
    }

    public static String decryptCBC(String data, String key, String charset) throws Exception {
        return new String(decryptCBC(Base64.decodeBase64(data), key.getBytes()), charset);
    }

    /*public static void main(String[] args) throws Exception {
        String a = decryptCBC("3L8qFcC_Git9WFi4nE3rC5ZzC_mnMliFdXzjfT3WKB-eNgd2ygJNojW4B5cOW-cWMpTl3rQlQYKVv9JgjYYNYsO3hhE5c3HbXlhkfxRjuLoYDIgvp2daEqzOgds6BTPAppIu3_YJNcYwOBlQCqTZqCmlrlzJGsbBbz2IDFZEv7UC86oMY_IitSBd17-OR2JueabGTN5remBXAiWVTI2-gw"
                ,"
                ","utf-8");
        System.out.println(a);
    }*/

}
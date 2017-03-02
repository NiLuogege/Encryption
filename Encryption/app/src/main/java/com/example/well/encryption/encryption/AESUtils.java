package com.example.well.encryption.encryption;

import android.os.Build;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ${LuoChen} on 2017/3/2.
 * email:luochen0519@foxmail.com
 * AES加密解密工具类
 */

public class AESUtils{
    private final static int JELLY_BEAN_4_2 = 17;
    private final static String HEX = "0123456789ABCDEF";
    /**
     * 加密
     *
     * @param key 秘钥
     * @param src 加密文本
     * @return
     * @throws Exception
     */

    public static String encrypt(String key, String src) throws Exception {
        byte[] rawkey = getRawKey(key.getBytes());
        byte[] result = encrypt(rawkey, src.getBytes());
        Log.d("AESUtils"," rawkey="+tStr(rawkey)+" result="+tStr(result));
        return toHex(result);
    }

    public static String decrypt(String key,String encrypted)throws Exception{
        byte[] rawKey = getRawKey(key.getBytes());
        byte[] enc  = toByte(encrypted);
        byte[] result   = decrypt(rawKey,enc);
        Log.d("AESUtils"," rawkey="+tStr(rawKey)+" enc="+tStr(enc)+" result="+tStr(result));
        return new String(result);
    }

    /**
     * 真正的加密过程
     *
     * @param key
     * @param src
     * @return
     */
    private static byte[] encrypt(byte[] key, byte[] src) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src);
        return encrypted;
    }

    /**
     * 真正解密的方法
     * @param key
     * @param encrypted
     * @return
     */
    private static byte[] decrypt(byte[] key, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec= new SecretKeySpec(key,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,skeySpec);
        byte[] decrypted=cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * 获取256位的加密秘钥
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    private static byte[] getRawKey(byte[] bytes) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_4_2) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(bytes);
        // 256 bits or 128 bits,192bits
        kgen.init(256, sr);
        SecretKey sKey = kgen.generateKey();
        byte[] raw = sKey.getEncoded();
        return raw;
    }

    private static byte[] toByte(String  hexString) {
        int len=hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i]=Integer.valueOf(hexString.substring(2*i,2*i+2),16).byteValue();
        }
        return result;
    }


    private static String toHex(byte[] buf) {
        if (buf == null) return "";

        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    private static String tStr(byte[] rawkey) {
        StringBuffer sb = new StringBuffer();
        for (byte b : rawkey) {
            sb.append(b).append(" ");
        }
        return sb.toString();
    }
}

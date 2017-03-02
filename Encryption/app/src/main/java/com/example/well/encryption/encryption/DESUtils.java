package com.example.well.encryption.encryption;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by ${LuoChen} on 2017/3/217:22.
 * email:luochen0519@foxmail.com
 * DES加密解密工具类
 */

public class DESUtils {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private final static String HEX = "12345678";

    /**
     * DES算法，加密
     *
     * @param data
     *            待加密字符串
     * @param key
     *            加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    public static String encode(String key,String data){
        if(data==null) return null;

        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv=new IvParameterSpec(HEX.getBytes());
            AlgorithmParameterSpec parameterSpec=iv;
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,parameterSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2String(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public static String decode(String key,String data){
        if(data==null) return null;

        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(HEX.getBytes());
            AlgorithmParameterSpec parameterSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE,secretKey,parameterSpec);
            return new String(cipher.doFinal(byte2Hex(data.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }

    }

    /**
     * 二进制转化成16进制
     *
     * @param b
     * @return
     */
    private static byte[] byte2Hex(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2String(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase(Locale.CHINA);
    }

}

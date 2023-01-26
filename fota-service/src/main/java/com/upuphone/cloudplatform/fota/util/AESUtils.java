package com.upuphone.cloudplatform.fota.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author guangzheng.ding
 * @date 2021/12/10 9:58
 */
public class AESUtils {
    private static String key = "AD42F6697B035B7580E4FEF93BE20BAD";
    private static String charset = "utf-8";
    private static int offset = 16;
    private static String transformation = "AES/CBC/PKCS5Padding";
    private static String algorithm = "AES";

    public static String encrypt(String content) {
        return encrypt(content, key);
    }

    public static String decrypt(String content) {
        return decrypt(content, key);
    }

    private static String encrypt(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, offset);

            Cipher cipher = Cipher.getInstance(transformation);
            byte[] byteContent = content.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
            String result = Base64.encode(cipher.doFinal(byteContent));
            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decrypt(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, offset);

            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skey, iv);
            byte[] result = cipher.doFinal(Base64.decode(content));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

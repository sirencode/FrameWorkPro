package com.syh.framework.algorithm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by shenyonghe on 2020-03-05.
 * 对称加密
 * ① 密钥长度：Key Size key的长度有三种：128、192、256（bits）。
 *    JDK默认只支持不大于128bits 的密钥，而128bits已能够满足商用需求，在此使用128bits长度。
 * ② 加密模式：Cipher Mode 块加密中有CBC、ECB、CTR、OFB、CFB等几种工作模式。
 * ③ 填充方式：Padding
 * ④ 初始向量：Initialization Vector
 */
public class AesUtil {
    private static String key = "128bitslength*@#";
    private static final String IV_STRING = "A-16-Byte-String";
    private static final String charset = "UTF-8";

    public static String EnCode(String content) {
        try {
            byte[] contentBytes = content.getBytes(charset);
            byte[] keyBytes = key.getBytes(charset);
            byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
            return Base64Util.encode(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DeCode(String content) {
        try {
            byte[] encryptedBytes = Base64Util.decode(content);
            byte[] keyBytes = key.getBytes(charset);
            byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
            return new String(decryptedBytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) {
        try {
            return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) {
        try {
            return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        byte[] initParam = IV_STRING.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, ivParameterSpec);
        return cipher.doFinal(contentBytes);
    }

    public static void main(String[] args)
            throws Exception {
        String pdaes = AesUtil.EnCode("100010014");
        System.out.println(pdaes);
        String pd = AesUtil.DeCode(pdaes);
        System.out.println(pd);
    }
}

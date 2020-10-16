package com.yc.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    /*已确认
     * 加密用的Key 可以用26个字母和数字组成
     * 此处使用AES-128-CBC加密模式，key需要为16位。
     */

    private static AESUtil instance=null;
    //private static
    private AESUtil(){

    }
    public static AESUtil getInstance(){
        if (instance==null)
            instance= new AESUtil();
        return instance;
    }

    // 加密
    public  String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        if(sSrc == null || "".equals(sSrc)){
            return "";
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
    }

    // 解密
    public String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        if(sSrc == null || "".equals(sSrc)){
            return "";
        }

        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }
   /* public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String cSrc = "123456";
        System.out.println("加密前的字串是："+cSrc);
        // 加密
        String enString = AESUtil.getInstance().encrypt(cSrc,"utf-8",sKey,ivParameter);
        System.out.println("加密后的字串是："+ enString);

        System.out.println("1jdzWuniG6UMtoa3T6uNLA==".equals(enString));

        // 解密
        String DeString = AESUtil.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
        System.out.println("解密后的字串是：" + DeString);
    }*/
}

package com.yc.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtils{

    // 1.定义密码字符串
    private static String SECRET = "!QAZ@WSX"; //任一字符串

    private static String ivParameter="1234567890123456";

   // 2.定义算法
    private static String AES = "AES";

    // 3.获取加密秘钥
    private static Key getKey(String secretStr) throws NoSuchAlgorithmException {
        //方式一：使用SecretKeySpec
        //SecretKey key = new SecretKeySpec(secretStr.getBytes(), AES);

        //方式二：使用KeyGenerator
        KeyGenerator genertor = KeyGenerator.getInstance(AES);
        genertor.init(128, new SecureRandom(secretStr.getBytes())); // 128 256...
        SecretKey key = genertor.generateKey();
        return key;
    }

    /**
     * 4.加密解密流文件(主要方法)
     */
    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }

    /**
     * 5.加密
     *
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void encAES(String srcPath, String enPath) throws IOException, GeneralSecurityException {
        File srcFile = new File(srcPath);
        File destFile  = new File(enPath);

        // 获取cipher对象  用于加密解密
        Key key = getKey(SECRET);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 注：AES需要填充方式，"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, key,iv);  // 加密是为ENCRYPT_MODE
        InputStream is = new FileInputStream(srcFile);
        OutputStream os = new FileOutputStream(destFile);
        crypt(is, os, cipher);
        os.close();
        is.close();

        /*
        * Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
        *
        * */
    }

    /**
     * 6.解密
     *
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void decAES(String enPath ,String destPath) throws IOException, GeneralSecurityException {

        File srcFile = new File(enPath);
        File destFile  = new File(destPath);
        // 获取cipher对象  用于加密解密
        Key key = getKey(SECRET);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 注：AES需要填充方式,"算法/模式/补码方式 // 加密是为ENCRYPT_MODE
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key,iv);
        InputStream is = new FileInputStream(srcFile);
        OutputStream os = new FileOutputStream(destFile);
        crypt(is, os, cipher);
        os.close();
        is.close();
    }
    public static void main(String[] args) throws Exception{
        String srcPath="D:/src.mp4";
        String destPath="D:/dest.mp4";
        String enPath = "D:/en.mp4";
        encAES(srcPath,enPath);
        decAES(enPath,destPath);
    }
}
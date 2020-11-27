/**
 * Copyright (c) 2020
 */

package com.yc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * base64编码解码工具类
 *
 * @author YC
 */
public class CommonBase64Utils {
    private static Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key){
        try{
            return new String(Base64Utils.decode(key.getBytes()));
        }catch (Exception e){
            return key;
        }

    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key){
        return new String(Base64Utils.encode(key.getBytes()));
    }
}
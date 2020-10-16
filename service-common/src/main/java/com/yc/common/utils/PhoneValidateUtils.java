/*
 * Copyright (c) 2017 citycloud.com.cn All Rights Reserved.
 */
package com.yc.common.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号规则验证
 * @author YC
 * @date 2019-3-15 13:57
 */
public class PhoneValidateUtils {
    private static final Random randomNumberGenerator = new Random();

    /**
     * 电信：133、153 、173(新) 、 177 (新) 、 180、181、189
     * 移动：134、135、136、137、 138、139、150、 151、152、157、158、159、 178(新)、 182、183、 184、187、 188
     * 联通：130、131、132、155、156、175 ( 新) 、176( 新) 、185、186
     * 数据卡：145、147、 149 其他：170(新)、171 (新)
     * <p>
     * 130、131、132、133, 134、135、136、137、 138、139
     * 180、181、182、183、 184、185、186、187、188、189
     * 150、 151、152、153 、    155、156、157、158、159、
     * 170(新)、171 (新)   173(新) 、 175 ( 新) 、176( 新) 、177 (新) 、 178(新)、
     * 145、147、 149
     */

    public static boolean isMobliePhone(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(19[0-9])|(16[0-9])|(18[0-9])|(15[^4,\\D])|(17[^2,^4,^9]))" +
                "\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static void assertPhone(String phone) {
        if (!isMobliePhone(phone)) {
            throw new IllegalArgumentException("错误的手机号格式");
        }
    }

    /**
     * 验证用户名只能是字母数字组合
     *
     * @param userName
     * @return
     */
    public static boolean validateUserName(String userName) {

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    /**
     * 生成4位随机数
     *
     * @return
     */
    public static int generateValidateCode() {
        return randomNumberGenerator.nextInt(900000) + 100000;
    }


    public static void main(String[] args) {

        System.out.println(PhoneValidateUtils.generateValidateCode());
    }
}

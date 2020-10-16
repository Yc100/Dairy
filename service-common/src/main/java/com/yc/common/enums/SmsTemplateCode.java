/*
 * Copyright (c) 2017 citycloud.com.cn All Rights Reserved.
 */
package com.yc.common.enums;

/**
 * 短信模版
 *
 * @author YC
 * @createDate 2020/10/16 14:33
 **/
public enum SmsTemplateCode {

    ValidateCode("531940", "示例短信验证码", "{\"code\":\"%s\"}");

    /**
     * 短信模版代码
     */
    private String templateCode;

    /**
     * 短信模版
     */
    private String messageTemplate;

    /**
     * 短信业务描述
     */
    private String desc;

    SmsTemplateCode(String templateCode, String desc, String messageTemplate) {
        this.templateCode = templateCode;
        this.desc = desc;
        this.messageTemplate = messageTemplate;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String getDesc() {
        return desc;
    }

    public static SmsTemplateCode resolve(String code) {
        for (SmsTemplateCode templateCode : SmsTemplateCode.values()) {
            if (templateCode.templateCode.equals(code)) {
                return templateCode;
            }
        }
        return null;
    }
}

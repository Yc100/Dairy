package com.yc.service.others;

import com.yc.common.entity.others.SmsMt;
import com.yc.common.enums.SmsTemplateCode;
import com.yc.common.service.BaseService;

/**
 * 短信发送信息表 业务逻辑处理接口
 *
 * @author YC
 * @since 1.0.0 2019-06-15
 */
public interface SmsMtService extends BaseService<SmsMt> {

    /**
     *
     * @Description: 短信入库
     * @author YC
     * @date 2019-06-15
     */
    void saveValidateCodeSms(SmsTemplateCode templateCode, String phoneNumber, Object... messages);
}
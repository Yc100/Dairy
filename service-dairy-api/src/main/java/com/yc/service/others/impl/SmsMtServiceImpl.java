package com.yc.service.others.impl;

import com.yc.common.entity.others.SmsMt;
import com.yc.common.enums.SmsTemplateCode;
import com.yc.common.service.impl.BaseServiceImpl;
import com.yc.dao.others.SmsMtDao;
import com.yc.service.others.SmsMtService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 短信发送信息表 服务实现类
 *
 * @author YC
 * @since 1.0.0 2019-06-15
 */
@Service
public class SmsMtServiceImpl extends BaseServiceImpl<SmsMtDao, SmsMt> implements SmsMtService {

    /**
     *
     * @Description: 验证码短信入库
     * @author YC
     * @date 2019-06-15
     */
    public void saveValidateCodeSms(SmsTemplateCode templateCode, String phoneNumber, Object... messages){


        // 短信模板代码 短信签名 不插入发送表，发送时使用默认值
        SmsMt mt = new SmsMt();
        mt.setPhoneNumber(phoneNumber);
        mt.setTemplateCode(templateCode.getTemplateCode());
        mt.setTemplateDesc(templateCode.getDesc());
        mt.setMessage(String.format(templateCode.getMessageTemplate(),messages));
        mt.setSendStatus(0);
        mt.setSendTimes(0);
        mt.setCreateTime(new Date());

        insert(mt);
    }
}
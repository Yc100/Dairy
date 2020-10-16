/**
 *
 */
package com.yc.common.utils;


import com.yc.common.entity.others.SmsMt;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lenovo
 *
 */
public class SmsUtils {

    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);


    //云通信产品-短信API服务产品名称（短信产品名固定，无需修改）
    private final static String product = "Dysmsapi";
    //云通信产品-短信API服务产品域名（接口地址固定，无需修改）
    private final static String domain = "dysmsapi.aliyuncs.com";
    //此处需要替换成开发者自己的AK信息
    private final static String secretId = "";
    private final static String secretKey = "";
    private final static String AppKey = "";

    //private final static String templateID = "111";
    private final static String appId="1400234510";

    //发送短信
    public static SendSmsResponse sendSms(SmsMt smsMt) throws Exception {

            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
             * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
             * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
             * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi*/
            Credential cred = new Credential(secretId, secretKey);

            // 实例化一个 http 选项，可选，无特殊需求时可以跳过
           // HttpProfile httpProfile = new HttpProfile();
//            // 设置代理
//            httpProfile.setProxyHost("host");
//            httpProfile.setProxyPort(port);
//             SDK 默认使用 POST 方法。
//             * 如需使用 GET 方法，可以在此处设置，但 GET 方法无法处理较大的请求
//            httpProfile.setReqMethod("POST");
//             SDK 有默认的超时时间，非必要请不要进行调整
//             * 如有需要请在代码中查阅以获取最新的默认值
//            httpProfile.setConnTimeout(60);
//             SDK 会自动指定域名，通常无需指定域名，但访问金融区的服务时必须手动指定域名
//             * 例如 SMS 的上海金融区域名为 sms.ap-shanghai-fsi.tencentcloudapi.com
//            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK 默认用 TC3-HMAC-SHA256 进行签名
             * 非必要请不要修改该字段 */
            clientProfile.setSignMethod("HmacSHA256");
            //clientProfile.setHttpProfile(httpProfile);
            /* 实例化 SMS 的 client 对象
             * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "",clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();

            /* 填充请求参数，这里 request 对象的成员变量即对应接口的入参
             * 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台：https://console.cloud.tencent.com/smsv2
             * sms helper：https://cloud.tencent.com/document/product/382/3773 */

            /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
            //String appid = "1400009099";
            req.setSmsSdkAppid(appId);

            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 */
            String sign = "";
            req.setSign(sign);

            /* 国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
            String senderid = "";
            req.setSenderId(senderid);

            /* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            //String session = "xxx";
            //req.setSessionContext(smsMt.getBizId());

            /* 短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
            //String extendcode = "";
            //req.setExtendCode(extendcode);

            /* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
            //String templateID = "400000";
            req.setTemplateID(smsMt.getTemplateCode());

            /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 例如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
            //String[] phoneNumbers = {"+8621212313123", "+8612345678902", "+8612345678903"};
            String[] phoneNumbers = {"+86" + smsMt.getPhoneNumber()};
            req.setPhoneNumberSet(phoneNumbers);

            /* 模板参数: 若无模板参数，则设置为空*/

            List<String> templateParamList = new ArrayList<>();
            LinkedHashMap<String, String> jsonMap = JSON.parseObject(smsMt.getMessage(), new TypeReference<LinkedHashMap<String, String>>() {});

            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                templateParamList.add(entry.getValue());
            }

            String[] templateParams = new String[templateParamList.size()];;

            templateParamList.toArray(templateParams);

            req.setTemplateParamSet(templateParams);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            // 输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));

            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());

        return res;
    }

    // 拉取单个号码短信下发状态
    public static PullSmsSendStatusByPhoneNumberResponse querySendStatus(SmsMt smsMt) throws Exception{

        /* 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
         * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
         * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
         * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi */
        Credential cred = new Credential(secretId, secretKey);

       /* // 实例化一个 http 选项，可选，无特殊需求时可以跳过。
        HttpProfile httpProfile = new HttpProfile();
        // 设置代理
        httpProfile.setProxyHost("host");
        httpProfile.setProxyPort(port);
        *//* SDK 默认使用 POST 方法。
         * 如需使用 GET 方法，可以在此处设置，但 GET 方法无法处理较大的请求 *//*
        httpProfile.setReqMethod("POST");
        *//* SDK 有默认的超时时间，非必要请不要进行调整
         * 如有需要请在代码中查阅以获取最新的默认值 *//*
        httpProfile.setConnTimeout(60);
        *//* SDK 会自动指定域名，通常无需指定域名，但访问金融区的服务时必须手动指定域名
         * 例如 SMS 的上海金融区域名为 sms.ap-shanghai-fsi.tencentcloudapi.com *//*
        httpProfile.setEndpoint("sms.tencentcloudapi.com");*/

        /* 非必要步骤:
         * 实例化一个客户端配置对象，可以指定超时时间等配置 */
        ClientProfile clientProfile = new ClientProfile();
        /* SDK 默认用 TC3-HMAC-SHA256 进行签名
         * 非必要请不要修改该字段 */
        clientProfile.setSignMethod("HmacSHA256");
        //clientProfile.setHttpProfile(httpProfile);

        /* 实例化 SMS 的 client 对象
         * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
        SmsClient client = new SmsClient(cred, "",clientProfile);

        /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
         * 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
         * 属性可能是基本类型，也可能引用了另一个数据结构
         * 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明 */
        PullSmsSendStatusByPhoneNumberRequest req = new PullSmsSendStatusByPhoneNumberRequest();

        /* 填充请求参数，这里 request 对象的成员变量即对应接口的入参
         * 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
         * 基本类型的设置:
         * 帮助链接：
         * 短信控制台：https://console.cloud.tencent.com/smsv2
         * sms helper：https://cloud.tencent.com/document/product/382/3773 */

        /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
        req.setSmsSdkAppid(appId);
        //设置起始时间为当前时间的前一小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //calendar.set(Calendar.HOUR,
                //calendar.get(Calendar.HOUR) - 1);
        calendar.set(Calendar.DATE,
                calendar.get(Calendar.DATE) - 1);
        //起始时间
        req.setSendDateTime(calendar.getTimeInMillis()/1000L);// UNIX 时间戳（时间：秒）
        // 设置拉取最大条数，最多100条
        req.setOffset(0L);
        Long limit = 100L;
        req.setLimit(limit);
        req.setPhoneNumber(smsMt.getPhoneNumber());
        /* 通过 client 对象调用 PullSmsSendStatus 方法发起请求。注意请求方法名与请求对象是对应的
         * 返回的 res 是一个 PullSmsSendStatusResponse 类的实例，与请求对象对应 */
        PullSmsSendStatusByPhoneNumberResponse res = client.PullSmsSendStatusByPhoneNumber(req);

        // 输出 JSON 格式的字符串回包
        System.out.println(PullSmsSendStatusResponse.toJsonString(res));

        return res;
    }

    public static SmsMt smsSendAndStatusQuery(SmsMt smsMt) throws Exception{
        SmsMt smsMtNew = new SmsMt();
        smsMtNew.setSmsId(smsMt.getSmsId());
        smsMtNew.setSendTime(new Date());

        SendSmsResponse sendResponse = sendSms(smsMt);
        SendStatus[] sendStatusArr = sendResponse.getSendStatusSet();

        smsMtNew.setRemarks("RequestId:" + sendResponse.getRequestId());// RequestId

        if(!ArrayUtils.isEmpty(sendStatusArr)) {
            smsMtNew.setRequestCode(sendStatusArr[0].getCode()); // SendStatus.code
            smsMtNew.setRequestMessage(sendStatusArr[0].getMessage());// SendStatus.Message
            smsMtNew.setBizId(sendStatusArr[0].getSerialNo());// SerialNo
        }
        smsMtNew.setSendTimes(smsMt.getSendTimes()+1);//发送次数

        Thread.sleep(3000L);

        PullSmsSendStatusByPhoneNumberResponse statusResponse = querySendStatus(smsMt);
        // 查询到返回的本手机号的发送状态中的SerialNo 循环编辑 若等于上一步短信发送的SerialNo
        // 则取ReportStatus 代表实际是否收到短信接收状态，SUCCESS（成功）、FAIL（失败）
        // 然后更新sms_mt数据库表的send_status字段
        PullSmsSendStatus[] pullSmsSendStatusArr = statusResponse.getPullSmsSendStatusSet();

        if(!ArrayUtils.isEmpty(pullSmsSendStatusArr)) {

            for(PullSmsSendStatus pullSmsSendStatus : pullSmsSendStatusArr){
                if(!StringUtils.isEmpty(pullSmsSendStatus.getSerialNo())
                        && pullSmsSendStatus.getSerialNo().equals(smsMtNew.getBizId())){
                    String reportStatus = pullSmsSendStatus.getReportStatus();
                    // ReportStatus SUCCESS（成功）置为3、FAIL（失败） 置为2
                    if (reportStatus.equals("SUCCESS")) {
                        smsMtNew.setSendStatus(3);
                    } else if (reportStatus.equals("FAIL")) {
                        smsMtNew.setSendStatus(2);
                    } else {
                        smsMtNew.setSendStatus(2);
                    }
                    break;
                }
            }
        }
        return smsMtNew;
    }

    public static SmsMt smsStatusQuery(SmsMt smsMt) throws Exception {
        SmsMt smsMtNew = new SmsMt();
        smsMtNew.setSmsId(smsMt.getSmsId());
        smsMtNew.setSendStatus(2);

        PullSmsSendStatusByPhoneNumberResponse statusResponse = querySendStatus(smsMt);
        // 查询到返回的本手机号的发送状态中的SerialNo 循环编辑 若等于上一步短信发送的SerialNo
        // 则取ReportStatus 代表实际是否收到短信接收状态，SUCCESS（成功）、FAIL（失败）
        //
        PullSmsSendStatus[] pullSmsSendStatusArr = statusResponse.getPullSmsSendStatusSet();

        if(!ArrayUtils.isEmpty(pullSmsSendStatusArr)) {

            for(PullSmsSendStatus pullSmsSendStatus : pullSmsSendStatusArr){
                if(!StringUtils.isEmpty(pullSmsSendStatus.getSerialNo())
                        && pullSmsSendStatus.getSerialNo().equals(smsMt.getBizId())){
                    String reportStatus = pullSmsSendStatus.getReportStatus();
                    // ReportStatus SUCCESS（成功）置为3、FAIL（失败） 置为2
                    if (reportStatus.equals("SUCCESS")) {
                        smsMtNew.setSendStatus(3);
                    } else if (reportStatus.equals("FAIL")) {
                        smsMtNew.setSendStatus(2);
                    } else {
                        smsMtNew.setSendStatus(2);
                    }
                    break;
                }
            }
        }
        return smsMtNew;
    }

    public static void main(String[] args) {
        SmsMt mt = new SmsMt();
        mt.setPhoneNumber("13994437646");
        mt.setTemplateCode("531940");
        mt.setMessage("{\"code\":\"456123\"}");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR,
                calendar.get(Calendar.HOUR) - 1);
        Date time = calendar.getTime();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
        System.out.println(sdf.format(time));
        try {
            smsSendAndStatusQuery(mt);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
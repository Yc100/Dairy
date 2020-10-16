package com.yc.common.entity.others;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 短信发送信息表
 *
 * @author YC
 * @createDate 2020/10/16 14:31
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sms_mt")
public class SmsMt {
	private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	@TableId("sms_id")
	private Long smsId;
    /**
     * 短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码
     */
	private String phoneNumber;
	/**
	 * 完整的短信内容
	 */
	private String message;
    /**
     * 阿里云短信模板代码 可为空，为空发送时取默认值SMS_130917724
     */
	private String templateCode;
	/**
	 * 短信业务描述
	 */
	private String templateDesc;
	/**
	 * 阿里云短信模板参数
	 * 封装短信模板所需要的模板中的变量JSON串
	 * 格式例如"{\"monitor_unit\":\"" + monitor_unit + "\", \"time\":\"" + time + "\", \"run_status\":\"" + run_status + "\"}"
	 */
	@TableField(exist = false)
	private String templateParam;
    /**
     * 阿里云短信签名 目前默认均为已审核通过的呼市第一医院 可为空，为空发送时取接口参数为默认值呼市第一医院
     */
	private String signName;
    /**
     * 最后一次实际发送时间
     */
	private Date sendTime;
    /**
     * 发送请求状态码
     */
	private String requestCode;
    /**
     * 发送请求状态码描述
     */
	private String requestMessage;
    /**
     * 发送请求回执ID,可根据该ID查询具体的发送状态
     */
	private String bizId;
    /**
     * 发送状态 0: 等待发送 1：等待回执，2：发送失败，3：发送成功 0是自己定义的，其余是阿里云短信接口定义的
     */
	private Integer sendStatus;
    /**
     * 发送次数 一次发送失败会重新发 有最大发送次数(3次)限制 最多发3次 初始为0
     */
	private Integer sendTimes;
    /**
     * 创建时间
     */
	@TableField(fill= FieldFill.INSERT)
	private Date createTime;
    /**
     * 备注
     */
	private String remarks;
}
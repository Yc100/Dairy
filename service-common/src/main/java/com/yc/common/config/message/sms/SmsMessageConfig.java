package com.yc.common.config.message.sms;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * @author YC
 * @createDate 2020/10/16 14:28
 **/
@Configuration
public class SmsMessageConfig {
    public static final String SMS_MESSAGE_QUNUE = "sms.business";

    /**
     * 短信业务队列
     *
     * @return
     */
    @Bean
    public Queue smsBusinessQueue() {
        return new Queue(SMS_MESSAGE_QUNUE);
    }


    /**
     * 短信交换
     *
     * @return
     */
    @Bean
    public DirectExchange smsBusinessDirectExchange() {
        return new DirectExchange(SMS_MESSAGE_QUNUE);
    }

    /**
     * 短信绑定
     *
     * @return
     */
    @Bean
    public Binding smsBusinessBinding() {
        return BindingBuilder.bind(smsBusinessQueue()).to(smsBusinessDirectExchange()).withQueueName();
    }
}

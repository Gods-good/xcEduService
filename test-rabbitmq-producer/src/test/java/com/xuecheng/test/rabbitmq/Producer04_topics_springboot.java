package com.xuecheng.test.rabbitmq;

import com.xuecheng.test.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试rabbitmq 入门程序
 *
 * @author Administrator
 * @version 1.0
 * @create 2018-06-29 9:22
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer04_topics_springboot {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //发送消息到mq
    @Test
    public void send_email(){
        //发送消息
        /**
         * 参数：String exchange, String routingKey, Object object
         * 1、交换机名称
         * 2、路由key
         * 3、消息内容
         */
        String message = "send email message..";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPICS_INFORM,"inform.email.sms",message);
//        System.out.println(message);
    }

}

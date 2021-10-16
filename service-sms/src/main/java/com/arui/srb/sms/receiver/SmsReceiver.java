package com.arui.srb.sms.receiver;

import com.arui.srb.rabbitutil.constant.MQConst;
import com.arui.srb.sms.service.SmsService;
import com.arui.srb.sms.utils.SmsProperties;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ...
 */
public class SmsReceiver {
    @Resource
    private SmsService smsService;

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQConst.QUEUE_SMS_ITEM, durable = "true"),
            exchange = @Exchange(value = MQConst.EXCHANGE_TOPIC_SMS),
            key = {MQConst.ROUTING_SMS_ITEM}
    ))
    public void send(Channel channel, Message message, String messageStr){
        System.out.println(message);
        System.out.println(messageStr);
        // 确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}

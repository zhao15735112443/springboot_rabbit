package com.zhao.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @version v1.0
 * @ProjectName: springboot_rabbit
 * @ClassName: MyCallBack
 * @Description: 回调
 * @Author: ming
 * @Date: 2022/3/26 17:14
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 将这个类注入到模板中
     * 依赖注入 rabbitTemplate 之后再设置它的回调对象
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * j交换机确认回调方法
     * @param correlationData 保存回调消息的ID及其相关信息   但是这个值如果不显示申请，其实是不存在，必须要在生产者申请
     * @param ack 交换机是否收到消息
     * @param cause 原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData == null ? "" : correlationData.getId();
        if (ack) {
            log.info("交换机已经收到 id 为:{}的消息",id);
        } else {
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}",id,cause);
        }
    }




    /**
     * 交换机不管是否收到消息的一个回调方法，当消息传递过程中不可达目的地时将消息返回生产者
     * CorrelationData
     * 消息相关数据
     * ack
     * 交换机是否收到消息
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error(" 消 息 {}, 被交换机 {} 退回，退回原因 :{}, 路 由 key:{}",new
                String(returned.getMessage().getBody()),returned.getExchange(),returned.getReplyText(),returned.getRoutingKey());
    }
}

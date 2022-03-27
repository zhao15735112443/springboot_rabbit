package com.zhao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @ProjectName: springboot_rabbit
 * @ClassName: ConfirmConsumer
 * @Description: 发布确认（高级）
 * @Author: ming
 * @Date: 2022/3/26 17:10
 */
@Component
@Slf4j
public class ConfirmConsumer {
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    @RabbitListener(queues =CONFIRM_QUEUE_NAME)
    public void receiveMsg(Message message){
        String msg=new String(message.getBody());
        log.info("接受到队列 confirm.queue 消息:{}",msg);
    }

    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    @RabbitListener(queues =BACKUP_QUEUE_NAME)
    public void receiveMsg2(Message message){
        String msg=new String(message.getBody());
        log.info("接受到队列 backup.queue 消息:{}",msg);
    }

}

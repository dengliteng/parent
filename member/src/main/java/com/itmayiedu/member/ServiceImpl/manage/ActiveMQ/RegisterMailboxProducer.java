package com.itmayiedu.member.ServiceImpl.manage.ActiveMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


@Service("registerMailboxProducer")
public class RegisterMailboxProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, String json){
        jmsMessagingTemplate.convertAndSend(destination,json);
    }
}

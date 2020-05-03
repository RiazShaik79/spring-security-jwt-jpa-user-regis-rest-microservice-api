package io.javabrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

//import static io.javabrains.config.ActiveMQConfig.ORDER_TOPIC;

@Service
public class EmailSender {

    private static Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendTopic(Email email) {
        log.info("sending with convertAndSend() to topic <" + email + ">");
        jmsTemplate.convertAndSend("mailbox", email);
    }

}

package io.javabrains;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

@Configuration
public class ActiveMQConfig {

    public static final String ORDER_TOPIC = "order-topic";
    
    @Value("${activemq.broker-url}")
	private String brokerUrl;
	

    @Bean
	public ActiveMQQueue queue() {
		return new ActiveMQQueue("mailbox");
	}
	
	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
	
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
			factory.setBrokerURL(brokerUrl);
			return factory;
	}
	
/*	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
	DefaultJmsListenerContainerFactoryConfigurer configurer) {
	DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	factory.setPubSubDomain(true);
	configurer.configure(factory, connectionFactory);
	return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(activeMQConnectionFactory()) ;
	}
	
	@Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper());
        return converter; 
        
	//return new MappingJackson2MessageConverter();
	//	return new SimpleMessageConverter();
    } 

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }  */
    


}
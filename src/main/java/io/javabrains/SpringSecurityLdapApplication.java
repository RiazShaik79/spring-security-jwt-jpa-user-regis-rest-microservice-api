package io.javabrains;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan
public class SpringSecurityLdapApplication {

	private static Logger log = LoggerFactory.getLogger(SpringSecurityLdapApplication.class);
	
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
	DefaultJmsListenerContainerFactoryConfigurer configurer) {
	DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	//factory.setPubSubDomain(true);
	configurer.configure(factory, connectionFactory);
	return factory;
	}
	
	  @Bean // Serialize message content to json using TextMessage
	  public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	  } 
	  

    @Bean
    @LoadBalanced
	public RestTemplate getRestTemplate() {
    	return new RestTemplate() ;
    	
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityLdapApplication.class, args);
	}
	
}

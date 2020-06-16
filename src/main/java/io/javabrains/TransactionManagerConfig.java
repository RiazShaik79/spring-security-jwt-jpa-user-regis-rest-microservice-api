package io.javabrains;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
public class TransactionManagerConfig {
	
	@Bean("jmstrans") 
	public JmsTransactionManager jmsTransactionManager (ConnectionFactory connectionFactory) {
		return new JmsTransactionManager(connectionFactory) ;	
	}
	
	@Bean("jpatrans")
	public JpaTransactionManager jpaTransactionManagerOther( ) {
		return new JpaTransactionManager();
	}

	@Bean("transactionManager")
	public JpaTransactionManager jpaTransactionManager( ) {
		return new JpaTransactionManager();
	}

}

package io.javabrains;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChainedTransactionConfig {

	@Bean("chainedTransactionManager")
	public PlatformTransactionManager chainedTransactionManager
			(@Qualifier("jmstrans") JmsTransactionManager jmsTransactionManager,
			@Qualifier("jpatrans") JpaTransactionManager jpaTransactionManager) {
		ChainedTransactionManager transactionManager = new 
				ChainedTransactionManager(jpaTransactionManager, jmsTransactionManager);
		
		return transactionManager;
		
		
	
	}
}

package io.javabrains;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@EnableCircuitBreaker
@SpringBootApplication
@EntityScan
@RefreshScope
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
    	RestTemplate restTemplate = new RestTemplate();
    	
    	KeyStore keyStore;
    	HttpComponentsClientHttpRequestFactory requestFactory = null;
    	
    	try {
    		keyStore = KeyStore.getInstance("jks") ;
    		ClassPathResource classPathResource = new ClassPathResource("user-regis-api-cert.jks");
    		InputStream inputStream = classPathResource.getInputStream();
    		keyStore.load(inputStream, "India330$$".toCharArray());
    		
    		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
    				new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
    				.loadKeyMaterial(keyStore, "India330$$".toCharArray()).build(),
    				NoopHostnameVerifier.INSTANCE) ;
    		
    		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
    				.setMaxConnTotal(Integer.valueOf(5))
    				.setMaxConnPerRoute(Integer.valueOf(5))
    				.build();
    		
    		requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    		requestFactory.setReadTimeout(Integer.valueOf(10000));
    		requestFactory.setConnectTimeout(Integer.valueOf(10000));
    		
    		restTemplate.setRequestFactory(requestFactory);
    		
    	} catch(Exception exception) {
    		System.out.println("Exception occured while creating rest templplate " + exception);
    		exception.printStackTrace();
     		
    	}
    	
    	
    	return restTemplate ;
    
    //	return new RestTemplate() ;
    	
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityLdapApplication.class, args);
	}
	
	 @Bean
		public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
		    DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		    System.setProperty("javax.net.ssl.keyStore", "src/main/resources/user-regis-api-cert.jks");
		    System.setProperty("javax.net.ssl.keyStorePassword", "India330$$");
		    System.setProperty("javax.net.ssl.trustStore", "src/main/resources/user-regis-api-cert.jks");
		    System.setProperty("javax.net.ssl.trustStorePassword", "India330$$");
		    EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
		    builder.withClientName("user-regis-api-cert");
		    builder.withSystemSSLConfiguration();
		    builder.withMaxTotalConnections(10);
		    builder.withMaxConnectionsPerHost(10);
		    args.setEurekaJerseyClient(builder.build());
		    return args;
		} 
	
}

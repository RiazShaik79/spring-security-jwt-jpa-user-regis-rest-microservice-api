package io.javabrains;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Collections;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

//import com.innovativeintelli.ldapauthenticationjwttoken.security.JwtAuthenticationEntryPoint;
@EnableOAuth2Sso
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/userlogin").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); 
	
	} 
	
	    @Bean(BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }   

	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return NoOpPasswordEncoder.getInstance();
	 }   

	    @Bean
	    @LoadBalanced
	    public RestOperations restTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oauth2ClientContext) {
	    	return new OAuth2RestTemplate(resource, oauth2ClientContext);
	    	
	    /*	RestOperations restTemplate = new OAuth2RestTemplate(resource, oauth2ClientContext);
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
	    		
	    		
	    		 ((HttpAccessor) restTemplate).setRequestFactory(requestFactory);
	    		
	    	} catch(Exception exception) {
	    		System.out.println("Exception occured while creating rest templplate " + exception);
	    		exception.printStackTrace();
	     		
	    	} 
	    	
	    	
	    	return restTemplate ;  */
	    }

}

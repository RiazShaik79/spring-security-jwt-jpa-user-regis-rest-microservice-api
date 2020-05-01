package io.javabrains;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SpringSecurityLdapApplication {

	
	private static Logger log = LoggerFactory.getLogger(SpringSecurityLdapApplication.class);

    

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityLdapApplication.class, args);
	}
	
}

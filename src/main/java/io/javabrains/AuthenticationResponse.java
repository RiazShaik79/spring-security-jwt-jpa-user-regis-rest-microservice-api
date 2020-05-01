package io.javabrains;

import org.springframework.security.core.Authentication;

public class AuthenticationResponse {
	
	private final Authentication authentication;
	
	public Authentication getAuthentication() {
		return authentication;
	}


	public AuthenticationResponse(Authentication auth) {
		
		authentication = auth;
	}

	
   public Authentication getAuth( ) {
		return authentication;
	}
	
	

}

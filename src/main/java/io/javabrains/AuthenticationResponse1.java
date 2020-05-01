package io.javabrains;

public class AuthenticationResponse1 {
	
	public AuthenticationResponse1(String jwt) {
		super();
		this.Jwt = jwt;
	}

	public AuthenticationResponse1() {
		
	}

	private String Jwt;
	
	public String getJwt( ) {
		return Jwt;
	}
	
	

}

package io.javabrains;

public class AuthenticationResponse1 {
	
	public AuthenticationResponse1(String jwt) {
		super();
		this.Jwt = jwt;
	}

	public AuthenticationResponse1() {
		
	}

	private String Jwt;
	
	public void setJwt(String jwt) {
		Jwt = jwt;
	}

	public String getJwt( ) {
		return Jwt;
	}
	
	

}

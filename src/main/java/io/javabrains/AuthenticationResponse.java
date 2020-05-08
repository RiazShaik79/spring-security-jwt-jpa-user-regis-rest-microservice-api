package io.javabrains;

public class AuthenticationResponse {
	
	public AuthenticationResponse(String authStatus) {
		
		this.authStatus = authStatus;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getJwt() {
		return Jwt;
	}

	public void setJwt(String jwt) {
		Jwt = jwt;
	}

	public AuthenticationResponse(String authStatus, String jwt) {
		super();
		this.authStatus = authStatus;
		Jwt = jwt;
	}
	
	private String authStatus;
	private String Jwt;


	
	public AuthenticationResponse() {
	
	}
	
}
	



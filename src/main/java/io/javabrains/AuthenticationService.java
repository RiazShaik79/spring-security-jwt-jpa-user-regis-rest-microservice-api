package io.javabrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class AuthenticationService {
	

	private AuthenticationResponse response = new AuthenticationResponse();
	
	@Autowired
	private jwtUtil jwtTokenUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "reliable")
	public ResponseEntity<?> validateAuthentication(AuthenticationRequest request) {
	
	try {
		
		response =
			restTemplate.postForObject("http://user-auth-service/authenticate", request, AuthenticationResponse.class);
			System.out.println(response.getAuthStatus()) ;
	}
	
	catch(Exception e) {
		System.out.println("error error : " + e);
	}
	if (response.getAuthStatus().equals("Authorized")) {
	String Jwt = jwtTokenUtil.generateToken(request.getUsername());
	response.setJwt(Jwt);
	return ResponseEntity.ok(response.getJwt());	
	}
	
	
  	return ResponseEntity.ok(response.getAuthStatus());	

}
	
	public ResponseEntity<?> reliable(AuthenticationRequest request) {
		  return ResponseEntity.ok("Cloud Native Java (O'Reilly)");
		  }
}

package io.javabrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class AuthenticationService {
	

	private AuthenticationResponse1 response = new AuthenticationResponse1();
	
	@Autowired
	private jwtUtil jwtTokenUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "reliable")
	public AuthenticationResponse1 validateAuthentication(AuthenticationRequest request) {
	
	try {
		
		response =
				restTemplate.postForObject("http://user-auth-service/authenticate", request, AuthenticationResponse1.class);
			System.out.println(response.getJwt()) ;
	}
	
	catch(Exception e) {
		System.out.println("error error : " + e);
	}
	
	String Jwt = jwtTokenUtil.generateToken(request.getUsername());
	
	response.setJwt(Jwt);
	
	return response;	

}
	
	public AuthenticationResponse1 reliable(AuthenticationRequest request) {
		  return new AuthenticationResponse1("Cloud Native Java (O'Reilly)");
		  }
}

package io.javabrains;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



//import com.innovativeintelli.ldapauthenticationjwttoken.security.JwtTokenProvider;

@RestController
public class HomeResource {
	
	private  Authentication authentication;
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private jwtUtil jwtTokenUtil;
	
	
	
	private AuthenticationResponse1 authenticationResponse;
	
	//private User user;
	
	@RequestMapping("/")
	public String index() {
		return "Home Page";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!..";
	}
	
	@RequestMapping(value="/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
	
				
		RestTemplate restTemplate = new RestTemplate();
		
		try {
		restTemplate.postForObject("http://localhost:8080/authenticate", authenticationRequest, AuthenticationResponse1.class);
		}
		
		catch(Exception e) {
			System.out.println("error error : " + e);
			return ResponseEntity.ok("UnAuthorized Credentials");	
		}
		
		String Jwt = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
		return ResponseEntity.ok(Jwt);	

	}
		
	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return UserService.getAllUsers();
	}

	@RequestMapping("/user/{Id}")
	public  Optional<User> getUser(@PathVariable int Id) {
		return UserService.getUser(Id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/Users")
	public void addUser(@RequestBody User User) {
		UserService.addUser(User);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/Users/{Id}")
	public void updateUser(@RequestBody User User, @PathVariable int Id) {
		UserService.updateUser(User, Id );
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/Users/{Id}")
	public void deleteUser(@PathVariable int Id) {
		UserService.deleteUser(Id);
	}

}

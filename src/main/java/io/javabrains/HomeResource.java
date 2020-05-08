package io.javabrains;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RefreshScope
public class HomeResource {
	
	private  Authentication authentication;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private jwtUtil jwtTokenUtil;
	
	private AuthenticationResponse authenticationResponse;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	//private User user;
	
	@RequestMapping("/")
	public String index() {
		return "Home Page";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!..";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		return authenticationService.validateAuthentication(authenticationRequest);
	}
		
	@RequestMapping("/users/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping("/user/{Id}")
	public  User getUser(@PathVariable int Id) {
		return userService.getUser(Id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/user/add")
	public void addUser(@RequestBody User User) {
		userService.addUser(User);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/user/update/{Id}")
	public void updateUser(@RequestBody User User, @PathVariable int Id) {
		userService.updateUser(User, Id );
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/user/delete/{Id}")
	public void deleteUser(@PathVariable int Id) {
		userService.deleteUser(Id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/user/forgotpassword/{Id}")
	public ResponseEntity<?> verifyUserandGenerateOTP(@PathVariable int Id) {
		System.out.println("In verifyUser and GenerateOTP service");
		User user = userService.getUser(Id);
		return otpService.sendOTP(user.getPhone());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/user/verifyOTP/{Id}")
	public  ResponseEntity<?> verifyOTP(@PathVariable int Id, @RequestBody OTPModel requestBodyotpModel) {
		User user = userService.getUser(Id);
		OTPModel otpModel = new OTPModel();
		otpModel.setMobilenumber(user.getPhone());
		otpModel.setOtp(requestBodyotpModel.getOtp());
		
		
		return otpService.verifyOTP(otpModel);
	
	}
	


}

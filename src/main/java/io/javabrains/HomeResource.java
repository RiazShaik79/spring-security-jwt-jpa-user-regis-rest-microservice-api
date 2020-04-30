package io.javabrains;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

//import com.innovativeintelli.ldapauthenticationjwttoken.security.JwtTokenProvider;

@RestController
public class HomeResource {
	
	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private jwtUtil jwtTokenUtil;
	
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
		
		
		Authentication authentication;
		try{
			 authentication = authenticationManager.authenticate(
		
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
 
		} 		catch(BadCredentialsException e) {
			System.out.println("error : " + e);
			throw new Exception("Incorrect username or password", e);
			
		}
		
		//final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String Jwt = jwtTokenUtil.generateToken(authentication);
		
		return ResponseEntity.ok(new AuthenticationResponse(Jwt));
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

package io.javabrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class OTPService {
	 private static Logger log = LoggerFactory.getLogger(EmailSender.class);

	 	//private OTPModel response;
	 	
	 	@Autowired
		private RestTemplate restTemplate;
		
		@HystrixCommand(fallbackMethod = "reliable1")
		public ResponseEntity<?> sendOTP(String mobilenumber) {
			
			OTPModel otpModel = new OTPModel();
			otpModel.setMobilenumber(mobilenumber);
		
		try {
			
			return ResponseEntity.ok(restTemplate.postForObject("http://otp-service/sendOTP/", otpModel, OTPModel.class).getOtp());
					
		}
		
		catch(Exception e) {
			System.out.println("error error : " + e);
			
			
		}
		return ResponseEntity.ok("Failed to generate OTP, please try agan..!");
		
	}
		
		public ResponseEntity<?> reliable1(String phoneNumber) {
		
			return ResponseEntity.ok("Cloud Native Java (O'Reilly)");
			
		}
		
		
		
		@HystrixCommand(fallbackMethod = "reliable2")
		public ResponseEntity<?> verifyOTP(OTPModel otpModel) {
			
			try {
			return ResponseEntity.ok(restTemplate.postForObject("http://otp-service/verifyOTP/", otpModel, OTPModel.class).getStatus());
			} 
			catch(Exception e) {
				System.out.println("error error : " + e);
			}
			
			return ResponseEntity.ok("OTP Verified Successfully..!!");
	}
		
		public ResponseEntity<?> reliable2(OTPModel otpModel) {
			
			return ResponseEntity.ok("Cloud Native Java (O'Reilly)");
	  }
}


package io.javabrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private EmailSender emailSender;
	
	@Cacheable(value = "Users")
	public List<User> getAllUsers() {
	//	simulateSlowService();
		List<User> Users = new ArrayList();
		UserRepository.findAll()
		.forEach(Users::add);
		return Users;
	}
	
	@Cacheable(value = "Users", key = "#Id")
	//@Cacheable("User")
	public User getUser(int Id) {
	//	simulateSlowService();
		return UserRepository.findById(Id).orElse(new User());
	}

	@CachePut(value = "Users", key = "#User.Id")
	@Transactional(transactionManager = "chainedTransactionManager")
	public User addUser(User User) {
		
		UserRepository.save(User);
		emailSender.sendTopic(new Email(User.getEmail(), "Hello","Dear " + User.getuserName() + ", You have been successfully registered" + "\n\n" + "Thanks"));
		// Don't do this if, in Prod -- this is only to test @Transactional 
		/* if (true) {
		throw new RuntimeException("Throwing exception to test transactional");
		} */
		return UserRepository.findById(User.getId()).orElse(new User());
	}
	
	@CachePut(value = "Users", key = "#Id")
	@Transactional(transactionManager = "chainedTransactionManager")
	public User updateUser(User User, int Id) {
		UserRepository.save(User);
		emailSender.sendTopic(new Email(User.getEmail(), "Hello","Dear " + User.getuserName() + ", Your updated details have been saved successfully" + "\n\n" + "Thanks"));
		return UserRepository.findById(User.getId()).orElse(new User());
		
	}
	
	@CacheEvict(value = "Users", key = "#Id") //to remove all entries user allEntries=true
	@Transactional(transactionManager = "chainedTransactionManager")
	public void deleteUser(int Id) {
		User user=UserRepository.findById(Id).orElse(new User());
		UserRepository.deleteById(Id);
		emailSender.sendTopic(new Email(user.getEmail(), "Hello","Dear " + user.getuserName() + ", Your data has been removed successfully" + "\n\n" + "Thanks"));
		
	}
	
	// Don't do this in Prod -- this is only to test caching 
		  private void simulateSlowService() {
		    try {
		      long time = 5000L;
		      Thread.sleep(time);
		    } catch (InterruptedException e) {
		      throw new IllegalStateException(e);
		    }
		  }

}

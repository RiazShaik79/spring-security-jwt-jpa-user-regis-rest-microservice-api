package io.javabrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private EmailSender emailSender;
	
		
	/*private List<User> Users = new ArrayList<>(Arrays.asList(
				new User("spring","Spring Framework","Spring Framework Description"),
				new User("java","Core Java","Core Java Description"),
				new User("javascript","JavaScript","JavaScript Description") */
	
	public List<User> getAllUsers() {
		List<User> Users = new ArrayList();
		UserRepository.findAll()
		.forEach(Users::add);
		return Users;
	}
	
	public User getUser(int Id) {
		return UserRepository.findById(Id).orElse(new User());
	}

	public void addUser(User User) {
		UserRepository.save(User);
		emailSender.sendTopic(new Email(User.getEmail(), "Hello","Dear " + User.getUserName() + ", You have been successfully registered" + "\n\n" + "Thanks"));
	}

	public void updateUser(User User, int Id) {
		UserRepository.save(User);
		emailSender.sendTopic(new Email(User.getEmail(), "Hello","Dear " + User.getUserName() + ", Your updated details have been saved successfully" + "\n\n" + "Thanks"));
		
	}

	public void deleteUser(int Id) {
		User user=UserRepository.findById(Id).orElse(new User());
		UserRepository.deleteById(Id);
		emailSender.sendTopic(new Email(user.getEmail(), "Hello","Dear " + user.getUserName() + ", Your data has been removed successfully" + "\n\n" + "Thanks"));
		
	}
}

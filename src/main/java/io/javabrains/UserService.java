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
	//	return Users;
		List<User> Users = new ArrayList();
		UserRepository.findAll()
		.forEach(Users::add);
		return Users;
	}
	
	public Optional<User> getUser(int Id) {
		return UserRepository.findById(Id);
	}

	public void addUser(User User) {
		UserRepository.save(User);
		emailSender.sendTopic(new Email("info@example.com", "Hello"));
	}

	public void updateUser(User User, int Id) {
		UserRepository.save(User);
		
	}

	public void deleteUser(int id) {

		UserRepository.deleteById(id);
		
	}
}

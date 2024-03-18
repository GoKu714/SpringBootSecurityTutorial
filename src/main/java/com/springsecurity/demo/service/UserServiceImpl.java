package com.springsecurity.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.demo.entity.User;
import com.springsecurity.demo.entity.VerificationToken;
import com.springsecurity.demo.model.UserModel;
import com.springsecurity.demo.repository.UserRepository;
import com.springsecurity.demo.repository.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	
	@Override
	public User registerUser(UserModel userModel) {
		User user = new User();
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setEmail(userModel.getEmail());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		user.setRole("USER");
		
		userRepository.save(user);
		// TODO Auto-generated method stub
		return user;
	}


	@Override
	public void saveVerificationTokenForUser(String token, User user) {
		// TODO Auto-generated method stub
		VerificationToken verificationToken = new VerificationToken(user, token);
		verificationTokenRepository.save(verificationToken);
	}
	
	public void sendMail(String toEmail, String subject, String body) {
		SimpleMailMessage simpleMailMesasge = new SimpleMailMessage();
		simpleMailMesasge.setFrom(emailFrom);
		simpleMailMesasge.setTo(toEmail);
		simpleMailMesasge.setSubject(subject);
		simpleMailMesasge.setText(body);
		javaMailSender.send(simpleMailMesasge);
	}

}

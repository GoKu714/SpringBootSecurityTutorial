package com.springsecurity.demo.service;

import com.springsecurity.demo.entity.User;
import com.springsecurity.demo.model.UserModel;

public interface UserService {

	User registerUser(UserModel userModel);

	void saveVerificationTokenForUser(String token, User user);
	
	void sendMail(String toEmail, String subject, String body);

}

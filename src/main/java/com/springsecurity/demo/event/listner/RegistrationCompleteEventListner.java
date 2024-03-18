package com.springsecurity.demo.event.listner;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.springsecurity.demo.entity.User;
import com.springsecurity.demo.event.RegistrationCompleteEvent;
import com.springsecurity.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent> {
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		// Create verification token for user with link
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.saveVerificationTokenForUser(token, user);
		//Send mail to user
		String url = event.getApplicationUrl() + "verifyRegistration?token=" + token;
		sendVerificationEmail(user);
		log.info("Click the link to verify your account: {}", url);
	}


	private void sendVerificationEmail(User user) {
		// TODO Auto-generated method stub
		var mailId = user.getEmail();
		userService.sendMail(mailId, "SpringBootAppTestSubject", "Sending Mail from spring boot application! Woohoo!");
	}
	
}

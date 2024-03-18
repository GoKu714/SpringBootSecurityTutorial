package com.springsecurity.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.demo.entity.User;
import com.springsecurity.demo.event.RegistrationCompleteEvent;
import com.springsecurity.demo.model.UserModel;
import com.springsecurity.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest httpServletRequest) {
		User user = userService.registerUser(userModel);
		applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(httpServletRequest)));
		return "SUCCESS";
	}

	private String applicationUrl(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() ;
	}
}

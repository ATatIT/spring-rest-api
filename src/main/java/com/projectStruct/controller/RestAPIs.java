package com.projectStruct.controller;


import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projectStruct.bean.UserBean;
import com.projectStruct.daoservices.userDao;
import com.projectStruct.exceptionhandeling.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class RestAPIs {
	
	userDao userService;
	
	
	public RestAPIs(userDao userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/hello")
	public  String helloUser() {
		return "Hello User";
	}
	
	@GetMapping("/users")
	public List<UserBean> allUser(){
		return userService.findAllUser();
	}
	
	@GetMapping("/users/{userId}")
	public UserBean userById(@PathVariable int userId) {
		UserBean user = userService.findUserById(userId); 
		if(user == null)
			throw new UserNotFoundException("id:"+ userId);
		
		return user;
	}
	
	@DeleteMapping("/users/{userId}")
	public void deleteUserById(@PathVariable int userId) {
		userService.deleteUserById(userId); 
	}
	
	// Enhancing POST Method to return correct HTTP Status Code and Location URI
	@PostMapping("/users")
	public ResponseEntity<UserBean> newUser(@Valid  @RequestBody UserBean user) {		
		UserBean saveUser = userService.saveUser(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saveUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();   
	}
	
	
	
	
	
}

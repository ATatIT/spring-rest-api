package com.projectStruct.controller;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projectStruct.bean.UserBean;
import com.projectStruct.entity.UserEntity;
import com.projectStruct.exceptionhandeling.UserNotFoundException;
import com.projectStruct.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
public class RestAPIs {

	private MessageSource massageSource;
	
	/*
	 * userDao userService;
	 * 
	 * public RestAPIs(userDao userService, MessageSource massageSource) { super();
	 * this.userService = userService; this.massageSource = massageSource; }
	 */
	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<UserEntity> allUser() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{userId}")
	public EntityModel<Optional<UserEntity>> userById(@PathVariable int userId) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if (user == null)
			throw new UserNotFoundException("id:" + userId);

		EntityModel<Optional<UserEntity>> entityModel = EntityModel.of(user); // for hateoas
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).allUser());

		entityModel.add(link.withRel("all-user"));

		return entityModel;
	}

	@DeleteMapping("/users/{userId}")
	public void deleteUserById(@PathVariable int userId) {
		userRepository.deleteById(userId);
	}

	// Enhancing POST Method to return correct HTTP Status Code and Location URI
	@PostMapping("/users")
	public ResponseEntity<UserBean> newUser(@Valid @RequestBody UserEntity user) {
		UserEntity saveUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/hello")
	public String helloUser() {
		Locale locale = LocaleContextHolder.getLocale();
		return massageSource.getMessage("good.morning.message", null, "Default Message", locale);
	}

}

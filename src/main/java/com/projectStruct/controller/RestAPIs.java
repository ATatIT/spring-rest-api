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
import com.projectStruct.entity.PostEntity;
import com.projectStruct.entity.UserEntity;
import com.projectStruct.exceptionhandeling.UserNotFoundException;
import com.projectStruct.repository.PostRepository;
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

	@Autowired
	PostRepository postRepository;
	
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

	// for posts

	@GetMapping("/users/{userId}/posts")
	public List<PostEntity> postForUser(@PathVariable int userId) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if (user == null)
			throw new UserNotFoundException("id:" + userId);

		return user.get().getPost();
	}
	
	@PostMapping("/users/{userId}/posts")
	public ResponseEntity<Object> savePostForUser(@PathVariable int userId,@Valid @RequestBody PostEntity post) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if (user == null)
			throw new UserNotFoundException("id:" + userId);
		
		post.setUser(user.get());
		PostEntity savePost =  postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savePost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

}

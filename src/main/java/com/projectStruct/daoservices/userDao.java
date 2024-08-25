package com.projectStruct.daoservices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.projectStruct.bean.UserBean;

@Component
public class userDao {
	
	private static int count = 0;
	private static List<UserBean> user = new ArrayList<>();
	
	static {
		user.add(new UserBean(++count, "Abhi", LocalDate.now().minusYears(22)));
		user.add(new UserBean(++count, "Rahul", LocalDate.now().minusYears(30)));
		user.add(new UserBean(++count, "Meet", LocalDate.now().minusYears(40)));
	}

	
	
	public List<UserBean> findAllUser(){
		return user;
	}
	
	public UserBean findUserById(Integer id) {
		Predicate<? super UserBean> predicate = user -> user.getId().equals(id);
		return user.stream().filter(predicate ).findFirst().orElse(null);
	}
	
	public void deleteUserById(Integer id) {
		Predicate<? super UserBean> predicate = user -> user.getId().equals(id);
		user.removeIf(predicate);
	}
	
	public UserBean saveUser(UserBean newUser) {
		newUser.setId(++count);
		user.add(newUser);
		return newUser;
	}
}

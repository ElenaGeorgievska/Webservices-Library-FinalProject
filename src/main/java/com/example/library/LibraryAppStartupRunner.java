package com.example.library;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.library.account.service.UserService;



@Component
public class LibraryAppStartupRunner implements CommandLineRunner {
	
	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {
		userService.createUser("admin", "password", "admin@library.com", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
	}

}

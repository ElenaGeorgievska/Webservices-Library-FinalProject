package com.example.library.account.service;

import java.security.Principal;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library.account.entity.User;



@Service
public class AccountService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserSecurityService userSecurityService;
	
	public String createAccount(@Valid User user, String password, BindingResult bindingResults, RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("email", user.getEmail());
		model.addAttribute("username", user.getUsername());	
		boolean invalidFields = false;
		if (bindingResults.hasErrors()) {
			return "redirect:/register";
		}
		invalidFields = userService.checkIfUsernameExists(user, redirectAttributes, invalidFields);
		invalidFields = userService.checkIfEmailExists(user, redirectAttributes, invalidFields);
		if (invalidFields) {
			return "redirect:/register";
		}		
		user = userService.createUser(user.getUsername(), password,  user.getEmail(), Arrays.asList("ROLE_USER"));	
		userSecurityService.authenticateUser(user.getUsername());
		return "redirect:/profile";  
	}

	public String updateUserInfo(User currentUser, User user, String newPassword, Model model, Principal principal) {
		if (!userService.isUsernameAvailable(user, currentUser, model)) {
			return "account/profile";
		}
		if (!userService.isEmailAvailable(user, currentUser, model)) {
			return "account/profile";
		}
		if (!userService.isPasswordCorrect(newPassword, user, currentUser, model)) {
			return "account/profile";
		}
		currentUser = userService.updateUserInfo(currentUser, user);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);				
		userSecurityService.authenticateUser(currentUser.getUsername());		
		return "account/profile";
	}

}

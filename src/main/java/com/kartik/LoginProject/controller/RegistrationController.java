package com.kartik.LoginProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.service.MyUserDetailsService;

@Controller
public class RegistrationController {
	@GetMapping("/register")
	String register(User user) {
		return "register";
	}
	
//	@Autowired
//	private UserRepo userRepo;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@PostMapping("/register")
	String RegisterUser(User user,RedirectAttributes redir) {
		if(userDetailsService.existsByUsername(user.getUsername())) {
			redir.addFlashAttribute("ErrorMessage", "Username already exists! Please A diiferent username.");
            return "redirect:/register";
		}
		userDetailsService.saveUser(user);
		redir.addFlashAttribute("successMessage", "Registration successful! You can now log in.");
		return "redirect:/login";
	}
}

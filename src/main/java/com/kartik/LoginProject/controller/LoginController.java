package com.kartik.LoginProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class LoginController {
	
	@GetMapping("/login")
	//Model is used here as after authentication fails, spring security reloads the login page on the same request.
	String login(@RequestParam(required= false)String error,
				 @RequestParam( required = false) String logout,
				 Model model) {
		if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
		if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully!");
		}
		return "login";
	
	}
	
		
}
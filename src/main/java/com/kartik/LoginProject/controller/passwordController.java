package com.kartik.LoginProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.service.MyUserDetailsService;

@Controller
public class passwordController {
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@GetMapping("/forgot-password")
	String forgotPassowrd(User user) {
		return "forgot-password";
	}
	
	@PostMapping("/forgot-password")
	//Using RedirectAttribues because we need to pass temporary message like success or error.
	String forgotPassowrdupdate(User user, RedirectAttributes redir) throws Exception {
		myUserDetailsService.updatePassword(user.getUsername(), user.getPassword());
		redir.addFlashAttribute("SuccessPassMessage", "Password changed successfully! You can now log in.");
		return "redirect:/login";
	}

}

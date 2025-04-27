package com.kartik.LoginProject.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kartik.LoginProject.model.UserPrincipal;

@Controller
public class EmployeeController {

	@GetMapping("/employee")
	public String employee(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		model.addAttribute("username", username);
		return "employee";
	}
}

package com.kartik.LoginProject.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kartik.LoginProject.model.UserPrincipal;

@Controller
public class FaqController {

	@GetMapping("/FAQ")
	//Using Model here because we are in the same request-response cycle and need to pass data to ThymLeaf template
	public String FAQ(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserPrincipal) {
		    model.addAttribute("username", ((UserPrincipal) principal).getUsername());
		} else {
		    System.out.println("Principal is not an instance of UserPrincipal: " + principal);
		}
//		String username = ((UserPrincipal) principal).getUsername();
//		model.addAttribute("username",username);
		return "FAQ";
	}
}

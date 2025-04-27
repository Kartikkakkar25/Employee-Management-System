package com.kartik.LoginProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.MyUserDetailsService;
import com.kartik.LoginProject.service.RosterService;

@Controller
public class RecruitmentController {
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private MyUserDetailsService userService;
	
	
	public String getFirstName(String fullName) {
	    if (fullName == null || fullName.trim().isEmpty()) {
	        return "";
	    }

	    String[] parts = fullName.trim().split("\\s+");
	    return parts[0]; // First word, even if it's the only one
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/recruitment")
	public String recruitment(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		Roster roster = new Roster();
		
		model.addAttribute("username", username);
		model.addAttribute("roster", roster);
		return "recruitment";
	}
	
	@PostMapping("/recruitment/submit")
	public String submitRecruitment(@ModelAttribute Roster roster, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		model.addAttribute("username", username);
		String userName = getFirstName(roster.getFullName());
		String password = userName+"@123";
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		userService.saveUser(user);
		
		user = userService.findByUsername(userName);
		
		System.out.println(user.getEmp_id());
		roster.setUser(user);
		rosterService.saveRoster(roster);
		model.addAttribute("successMessage", "Details submitted successfully!");
		return "recruitment";
	}
}

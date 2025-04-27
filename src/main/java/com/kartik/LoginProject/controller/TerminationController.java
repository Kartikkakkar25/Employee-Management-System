package com.kartik.LoginProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//import com.kartik.LoginProject.dao.RosterRepository;
import com.kartik.LoginProject.dao.UserRepo;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.Termination;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.RosterService;
import com.kartik.LoginProject.service.TerminationService;

@Controller
public class TerminationController {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TerminationService termService;
	@Autowired
	private RosterService rosterService;
	
	@GetMapping("/termination")
	public String termination(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		
		List<User> users = userRepo.findAll();
        
        Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user1 : users) {
            Roster roster1 = rosterService.getRosterByUser(user1);
//            userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : "N/A");
            userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : user1.getUsername());
        }
		
        Termination termination = new Termination();
        termination.setUser(new User());
        
        model.addAttribute("userFullNameMapJson", userFullNameMap);
		model.addAttribute("username", username);
		model.addAttribute("userFullNameMap", userFullNameMap);
		model.addAttribute("termination",termination);
		model.addAttribute("terminationStatusList", List.of("Internal Transfer", "Job Abandonment", "Resignation", "Termination", "Training Removal"));
		model.addAttribute("terminationReasonList", List.of("Resigned", "On Bench", "Fired", "Other"));

		return "termination";
	}
	
	
	
	@PostMapping("/termination/submit")
	public String submitTerminationForm(@ModelAttribute Termination termination, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		
		Roster roster = rosterService.getRosterByUser(termination.getUser());
		if (termination.getRehire() == null) {
	        termination.setRehire("false");
	    }
		termService.saveTermination(termination, roster);
		

		List<User> users = userRepo.findAll();

		Map<Integer, String> userFullNameMap = new HashMap<>();
		for (User user1 : users) {
			Roster roster1 = rosterService.getRosterByUser(user1);
			userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : "N/A");
		}

		model.addAttribute("userFullNameMapJson", userFullNameMap);
		model.addAttribute("username", username);
		model.addAttribute("userFullNameMap", userFullNameMap);
		model.addAttribute("terminationStatusList", List.of("Internal Transfer", "Job Abandonment", "Resignation", "Termination", "Training Removal"));
		model.addAttribute("terminationReasonList", List.of("Resigned", "On Bench", "Fired", "Other"));
		model.addAttribute("successMessage", "Termination form submitted successfully!");

		model.addAttribute("termination", new Termination());
		return "termination";
	}
}

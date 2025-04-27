package com.kartik.LoginProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kartik.LoginProject.dao.UserRepo;
import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.RosterService;

@Controller
public class AmmendmentController {

	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/amendment")
	public String amendment(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		model.addAttribute("username", username);
		
		List<User> users = userRepo.findAll();
		Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user1 : users) {
            Roster roster1 = rosterService.getRosterByUser(user1);
//            userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : "N/A");
            userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : user1.getUsername());
        }
		
		model.addAttribute("userFullNameMap", userFullNameMap);
		model.addAttribute("attendance", new Attendance());
		return "amendment";
	}
}

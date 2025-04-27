package com.kartik.LoginProject.controller;

//import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.RosterService;
import com.kartik.LoginProject.dao.UserRepo;
//import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;


@Controller
public class RosterController {

	private final UserRepo userRepo;
    private final RosterService rosterService;

	public RosterController(UserRepo userRepo, RosterService rosterService) {
		this.userRepo = userRepo;
        this.rosterService=rosterService;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/roster")
	public String Roster(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();
		Roster roster = new Roster();
		
		List<User> users = userRepo.findAll();
        
        Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user1 : users) {
            Roster roster1 = rosterService.getRosterByUser(user1);
            userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : "N/A");
        }
        model.addAttribute("userFullNameMap", userFullNameMap);
        model.addAttribute("users", users);
		model.addAttribute("username", username);
		model.addAttribute("roster", roster); // Add empty object for form binding

		return "roster";
	}
	
	
	@PostMapping("/roster/fetch")
	public String fetchAttendance(@RequestParam("empId") int empId, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
        
	    User user = userRepo.findById(empId).orElse(null);
	    List<User> users = userRepo.findAll();
	    
	    Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user1 : users) {
            Roster roster = rosterService.getRosterByUser(user1);
            userFullNameMap.put(user.getEmp_id(), (roster != null) ? roster.getFullName() : "N/A");
        }
        
        model.addAttribute("users", users); // Fetch all users
        model.addAttribute("username", username);
	    model.addAttribute("userFullNameMap", userFullNameMap);
	    model.addAttribute("selectedEmpId", empId);
	    
	    
	    Roster roster = rosterService.getRosterByUser(user);
	    if (user == null || roster == null) {
	        roster = new Roster();
	        roster.setUser(user);
	    } else {
	    	roster = rosterService.getRosterByUser(user);
	        
	    }
	    model.addAttribute("roster", roster);
	    return "roster";
	}
	
	
	
	
	
	@PostMapping("/roster")
    public String submitRoster(@ModelAttribute Roster roster, @RequestParam boolean updateMode, @RequestParam("empId") int empId, Model model, 
    							RedirectAttributes redirectAttributes) {
	    // Fetch the user from the database
	    User user = userRepo.findById(empId).orElse(null);
	    
	    if (user == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "User not found! Cannot submit roster.");
	        return "redirect:/roster";
	    }
		
		
		
        Roster existingRoster = rosterService.getRosterByUser(user);
        
		if (updateMode) {
			if (existingRoster != null) {
				existingRoster.setFullName(roster.getFullName());
	            existingRoster.setAddress1(roster.getAddress1());
	            existingRoster.setAddress2(roster.getAddress2());
	            existingRoster.setCity(roster.getCity());
	            existingRoster.setState(roster.getState());
	            existingRoster.setPostalCode(roster.getPostalCode());
	            
	            existingRoster.setSundayShiftStart(roster.getSundayShiftStart());
	            existingRoster.setSundayShiftEnd(roster.getSundayShiftEnd());
	            
	            existingRoster.setMondayShiftStart(roster.getMondayShiftStart());
	            existingRoster.setMondayShiftEnd(roster.getMondayShiftEnd());
	            
	            existingRoster.setTuesdayShiftStart(roster.getTuesdayShiftStart());
	            existingRoster.setTuesdayShiftEnd(roster.getTuesdayShiftEnd());

	            existingRoster.setWednesdayShiftStart(roster.getWednesdayShiftStart());
	            existingRoster.setWednesdayShiftEnd(roster.getWednesdayShiftEnd());

	            existingRoster.setThursdayShiftStart(roster.getThursdayShiftStart());
	            existingRoster.setThursdayShiftEnd(roster.getThursdayShiftEnd());

	            existingRoster.setFridayShiftStart(roster.getFridayShiftStart());
	            existingRoster.setFridayShiftEnd(roster.getFridayShiftEnd());

	            existingRoster.setSaturdayShiftStart(roster.getSaturdayShiftStart());
	            existingRoster.setSaturdayShiftEnd(roster.getSaturdayShiftEnd());

	            
	            existingRoster.toString();
	            rosterService.updateRoster(existingRoster);



	            redirectAttributes.addFlashAttribute("successMessage", "Roster updated successfully!");
				return "redirect:/roster";
			}
			else {
	            redirectAttributes.addFlashAttribute("errorMessage", "No existing roster found to update.");
			}
		}
		else {
	        // ✅ Create new roster only if no existing one is found
	        if (existingRoster != null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "User already has a roster entry. Switch to update mode.");
	            return "redirect:/roster";
	        }
        roster.setUser(user);
        rosterService.saveRoster(roster);
        redirectAttributes.addFlashAttribute("successMessage", "Details submitted successfully!");
        model.addAttribute("selectedEmpId", empId);
		}
        return "redirect:/roster";
    }
}

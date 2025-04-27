package com.kartik.LoginProject.controller;

//import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kartik.LoginProject.dao.RosterRepository;
import com.kartik.LoginProject.dao.UserRepo;
import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.AttendanceService;

@Controller
public class ApprovalController {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RosterRepository rosterRepo;
//	@Autowired
//	private AttendanceRepo attendanceRepo;
	
	@GetMapping("/attendance/approval")
	public String getApprovalPage(Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
        
        List<User> users = userRepo.findAll();
        
        Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user : users) {
            Roster roster = rosterRepo.findByUser(user);
            userFullNameMap.put(user.getEmp_id(), (roster != null) ? roster.getFullName() : "N/A");
        }
        model.addAttribute("username", username);
        model.addAttribute("users", users); // Fetch all users
	    model.addAttribute("userFullNameMap", userFullNameMap);
	    return "attendanceApproval"; // New HTML page for approval
	}
	
	
	
	@PostMapping("/attendance/fetch")
	public String fetchAttendance(@RequestParam("empId") int empId, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
        
	    User user = userRepo.findById(empId).orElse(null);
	    List<User> users = userRepo.findAll();
	    
	    Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user1 : users) {
            Roster roster = rosterRepo.findByUser(user1);
            userFullNameMap.put(user.getEmp_id(), (roster != null) ? roster.getFullName() : "N/A");
        }
        
        model.addAttribute("users", users); // Fetch all users
        model.addAttribute("username", username);
	    model.addAttribute("userFullNameMap", userFullNameMap);
	    model.addAttribute("selectedEmpId", empId);
	    
	    
	    if (user == null) {
	        model.addAttribute("attendance", Collections.emptyList());
	    } else {
//	        LocalDateTime start = LocalDateTime.now().withDayOfMonth(11).minusMonths(1);
//	        LocalDateTime end = LocalDateTime.now().withDayOfMonth(10);
	        List<Attendance> attendanceRecords = attendanceService.getByUser(user);
//	        model.addAttribute("attendance", attendanceRecords.isEmpty() ? "--" : attendanceRecords);
	        model.addAttribute("attendance", attendanceRecords);
	    }
	    return "attendanceApproval";
	}
	
	@PostMapping("/attendance/approve")
	public String approveAttendance(@RequestParam("attendanceId") int id) {
	    attendanceService.approveAttendance(id);
	    return "redirect:/attendance/approval";
	}
	
}

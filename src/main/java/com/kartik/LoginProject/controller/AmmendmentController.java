package com.kartik.LoginProject.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kartik.LoginProject.TimeCalculator.TimeCalculator;
import com.kartik.LoginProject.dao.RosterRepository;
import com.kartik.LoginProject.dao.UserRepo;
import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.AttendanceService;
import com.kartik.LoginProject.service.MyUserDetailsService;

@Controller
public class AmmendmentController {

	@Autowired
	private RosterRepository rosterService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private MyUserDetailsService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
    private TimeCalculator timeCalculator;
	@GetMapping("/amendment")
	public String amendment(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserPrincipal) principal).getUsername();

		List<User> users = userService.getAll();
		Map<Integer, String> userFullNameMap = new HashMap<>();
        for (User user1 : users) {
            Roster roster1 = rosterService.findByUser(user1);
            userFullNameMap.put(user1.getEmp_id(), (roster1 != null) ? roster1.getFullName() : user1.getUsername());
        }
        model.addAttribute("username", username);
		model.addAttribute("users", users);
		model.addAttribute("userFullNameMap", userFullNameMap);
		model.addAttribute("attendance", new Attendance());
		model.addAttribute("selectedEmpId", null);
        model.addAttribute("selectedDate", null);
		return "amendment";
	}
	
	@PostMapping("/amendment/fetch")
	public String fetchAmendment(@RequestParam("empId") int empId,
								 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
								 Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
		List<User> users = userService.getAll();
		
		User user = userService.getByEmpId(empId).orElse(null);
		Attendance attendance = attendanceService.getAttendanceByDate(user, date);
		
		model.addAttribute("users", users);
        model.addAttribute("selectedEmpId", empId);
        model.addAttribute("selectedDate", date);
        if (attendance == null) {
            model.addAttribute("notFound", true);
        } else {
            model.addAttribute("attendance", attendance);
        }
        model.addAttribute("username", username);
		return "amendment";
	}
	
	@PostMapping("/amendment/save")
    public String saveAmendment(@RequestParam("empId")int empId ,
    							@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    							@ModelAttribute Attendance attendance) {
		
		User user = userRepo.findById(empId).orElse(null);
		Attendance existingAttendance = attendanceService.getAttendanceByDate(user, date);
		if(existingAttendance!=null) {
			existingAttendance.setLoginTime(attendance.getLoginTime());
			existingAttendance.setLogoutTime(attendance.getLogoutTime());
			float actualHours = timeCalculator.ActualTimeCalculator(attendance.getLoginTime(), attendance.getLogoutTime());
	        existingAttendance.setActualHours(actualHours);
	        float diff = (existingAttendance.getScheduledHours()) - (actualHours);
	        existingAttendance.setDifference(diff);
	        existingAttendance.setStatus("Pending");
		}
		
        attendanceService.saveAttendance(existingAttendance);
        return "redirect:/amendment?success";
    }
}

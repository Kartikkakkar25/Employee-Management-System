package com.kartik.LoginProject.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kartik.LoginProject.DTO.AttendanceDTO;
import com.kartik.LoginProject.TimeCalculator.TimeCalculator;
import com.kartik.LoginProject.dao.UserRepo;
import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.model.UserPrincipal;
import com.kartik.LoginProject.service.AttendanceService;
import com.kartik.LoginProject.service.RosterService;

@Controller
public class AttendanceController {
	
	 String currentDay = (java.time.LocalDate.now().getDayOfWeek().name()).substring(0, 1).toUpperCase() + (java.time.LocalDate.now().getDayOfWeek().name()).substring(1).toLowerCase();
	
	@Autowired
	 private AttendanceService attendanceService;
	@Autowired
	 private RosterService rosterService;
	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
    private TimeCalculator timeCalculator;
	
	@GetMapping("/attendance")
	@PreAuthorize("hasRole('USER')")
    public String attendance(Model model) {
		
		 
		 
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
        User user = userRepo.findByUsername(username);
        int CurrEmpId=user.getEmp_id();
        Roster roster = rosterService.getRosterByUser(user);
       
        if (user != null) {
//            LocalDate today = LocalDate.now();
//            List<Attendance> weeklyAttendance = IntStream.rangeClosed(0, 6)
//                    .mapToObj(i -> today.minusDays(i))
//                    .map(date -> attendanceService.getAttendanceByDate(user, date))
//                    .collect(Collectors.toList());
            List<AttendanceDTO> weeklyAttendance = attendanceService.getWeeklyAttendance(user);
            model.addAttribute("attendanceList", weeklyAttendance);

//            model.addAttribute("weeklyAttendance", weeklyAttendance);
        }
        
        if (roster != null) {
        	String fullname=roster.getFullName();
        	model.addAttribute("fullName", fullname);

            float scheduledHours = timeCalculator.TimeCalculating(currentDay, user);
            model.addAttribute("scheduledHours", String.valueOf(scheduledHours));
        }
        else {
        	model.addAttribute("NoRosterMessage", "Roster Form Does Not Exists! Please Contact ADMIN");
        	model.addAttribute("empId", CurrEmpId);
            model.addAttribute("username", username);
        	return "attendance";
        }
//        List<String> daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        
        model.addAttribute("empId", CurrEmpId);
//        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("username", username);
        model.addAttribute("currentDay", currentDay);
//        model.addAttribute("selectedDay", day);
        return "attendance";
    }
	
	@GetMapping("/admin/attendance")
	@PreAuthorize("hasRole('ADMIN')")
	public String attendanceAdmin(@RequestParam(value = "day", required = false, defaultValue = "Monday") String day,Model model) {
		
		 
		 
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
        User user = userRepo.findByUsername(username);
        int CurrEmpId=user.getEmp_id();
        Roster roster = rosterService.getRosterByUser(user);
        String userRole = user.getRole().equals("ROLE_ADMIN") ? "ADMIN" : "USER"; // Determine role
        model.addAttribute("userRole", userRole);
       
        if (user != null) {
//            LocalDate today = LocalDate.now();
//            List<Attendance> weeklyAttendance = IntStream.rangeClosed(0, 6)
//                    .mapToObj(i -> today.minusDays(i))
//                    .map(date -> attendanceService.getAttendanceByDate(user, date))
//                    .collect(Collectors.toList());
            List<AttendanceDTO> weeklyAttendance = attendanceService.getWeeklyAttendance(user);
            model.addAttribute("attendanceList", weeklyAttendance);

//            model.addAttribute("weeklyAttendance", weeklyAttendance);
        }
        
        if (roster != null) {
        	String fullname=roster.getFullName();
        	model.addAttribute("fullName", fullname);

            float scheduledHours = timeCalculator.TimeCalculating(day, user);
            model.addAttribute("scheduledHours", String.valueOf(scheduledHours));
        }
        else {
        	model.addAttribute("NoRosterMessage", "Roster Form Does Not Exists! Please Contact ADMIN");
        	model.addAttribute("empId", CurrEmpId);
            model.addAttribute("username", username);
        	return "attendance";
        }
        List<String> daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        
        model.addAttribute("empId", CurrEmpId);
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("username", username);
//        model.addAttribute("currentDay", currentDay);
        model.addAttribute("selectedDay", day);
        return "attendance";
    }
	
	
	@PostMapping("/attendance/submit")
    public String submitAttendance(@RequestParam("day") String day,
    							   @RequestParam("loginTime") String loginTime, 
                                   @RequestParam("logoutTime") String logoutTime, 
                                   Model model, RedirectAttributes redirectAttributes) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserPrincipal) principal).getUsername();
        User user = userRepo.findByUsername(username);
        Roster roster = rosterService.getRosterByUser(user);
        
        Attendance attendance = new Attendance();
        

        float scheduledHours = timeCalculator.TimeCalculating(day, user);
        attendance.setUser(user);
        attendance.setFullName(roster.getFullName());
        attendance.setLoginTime(loginTime);
        attendance.setLogoutTime(logoutTime);
        attendance.setDay(day);
        float actualHours = timeCalculator.ActualTimeCalculator(loginTime, logoutTime);
        attendance.setActualHours(actualHours);
        attendance.setScheduledHours(scheduledHours);
        attendance.setSubmissionTimestamp(LocalDateTime.now());
        attendance.setStatus("Pending");
        float diff = (attendance.getScheduledHours()) - (attendance.getActualHours());
        attendance.setDifference(diff);
        attendanceService.saveAttendance(attendance);
        redirectAttributes.addFlashAttribute("successMessage", "Attendance submitted successfully!");

        return "redirect:/attendance";
    }
	
}

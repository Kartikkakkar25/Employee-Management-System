package com.kartik.LoginProject.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kartik.LoginProject.DTO.AttendanceDTO;
import com.kartik.LoginProject.TimeCalculator.TimeCalculator;
import com.kartik.LoginProject.dao.AttendanceRepo;
import com.kartik.LoginProject.model.Attendance;
import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
	private RosterService rosterService;
    
    @Autowired
    private TimeCalculator timeCalculator;

    public void saveAttendance(Attendance attendance) {
        // Fetch full name from Roster using emp_id
    	
    	if (attendance == null || attendance.getUser() == null) {
            throw new IllegalArgumentException("Attendance or User cannot be null");
        }

        // Fetch full name from Roster using emp_id
    	Roster roster = rosterService.getRosterByUser(attendance.getUser());
        attendance.setFullName((roster != null) ? roster.getFullName() : "Unknown");
//        attendance.setStatus("Pending");

        attendanceRepo.save(attendance);
    }
    public Attendance getAttendanceByDate(User user, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 23:59:59.999999999
        Optional<Attendance> attendance = attendanceRepo.findByUserAndSubmissionTimestampBetween(user, startOfDay, endOfDay);
        return attendance.orElse(null);
    }
    
    public List<AttendanceDTO> getWeeklyAttendance(User user){
    	List<AttendanceDTO> weeklyAttendance = new ArrayList<>();
    	LocalDate today = LocalDate.now();
    	LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
    	
    	for(int i = 0; i < 7; i++) {
    		LocalDate date = startOfWeek.plusDays(i);
    		Optional<Attendance> attendanceOpt = attendanceRepo.findByUserAndSubmissionTimestampBetween(user, date.atStartOfDay(), date.atTime(LocalTime.MAX));
    		
    		AttendanceDTO dto = new AttendanceDTO();
    		dto.setDate(date);
    		dto.setDayName(date.getDayOfWeek().toString());
    		
    		if(attendanceOpt.isPresent()) {
    			Attendance attendance = attendanceOpt.get();
    			dto.setLoginTime(attendance.getLoginTime());
    			dto.setLogoutTime(attendance.getLogoutTime());
    			dto.setScheduledHours(attendance.getScheduledHours());
    			dto.setActualHours(attendance.getActualHours());
    			dto.setStatus(attendance.getStatus());
    		}
    		else{
    			float scheduledHours =timeCalculator.TimeCalculating(dto.getDayName(), user);
    			dto.setLoginTime("--:--");
    			dto.setLogoutTime("--:--");
    			dto.setScheduledHours(scheduledHours);
    			dto.setActualHours(0.0f);
    			dto.setStatus("--");
    		}
    		weeklyAttendance.add(dto);
    	}
    	
    	return weeklyAttendance;
    }
    
    public List<Attendance> getAllByUserAndSubmissionTimestampBetween(User user, LocalDateTime startOfDay,LocalDateTime endOfDay){
    	return attendanceRepo.findAllByUserAndSubmissionTimestampBetween(user, startOfDay, endOfDay);
    }
    
    public void approveAttendance(int id) {
        Attendance attendance = attendanceRepo.findById(id).orElse(null);
        if (attendance != null) {
            attendance.setStatus("Approved");
            attendanceRepo.save(attendance);
        }
    }
    public List<Attendance> getAll(){
    	return attendanceRepo.findAll();
    }
    
    public List<Attendance> getByUser(User user){
    	return attendanceRepo.findByUser(user);
    }
}

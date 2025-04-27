package com.kartik.LoginProject.TimeCalculator;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kartik.LoginProject.model.Roster;
import com.kartik.LoginProject.model.User;
import com.kartik.LoginProject.service.RosterService;


@Service
public class TimeCalculator {
	
	 @Autowired
	 private RosterService rosterService;
	
	public float TimeCalculating(String dayName, User user) {
		
		Roster roster = rosterService.getRosterByUser(user);
        String shiftStart = "", shiftEnd = "";
        switch (dayName.toLowerCase()) {
            case "monday":
                shiftStart = roster.getMondayShiftStart();
                shiftEnd = roster.getMondayShiftEnd();
                break;
            case "tuesday":
                shiftStart = roster.getTuesdayShiftStart();
                shiftEnd = roster.getTuesdayShiftEnd();
                break;
            case "wednesday":
                shiftStart = roster.getWednesdayShiftStart();
                shiftEnd = roster.getWednesdayShiftEnd();
                break;
            case "thursday":
                shiftStart = roster.getThursdayShiftStart();
                shiftEnd = roster.getThursdayShiftEnd();
                break;
            case "friday":
                shiftStart = roster.getFridayShiftStart();
                shiftEnd = roster.getFridayShiftEnd();
                break;
            case "saturday":
                shiftStart = roster.getSaturdayShiftStart();
                shiftEnd = roster.getSaturdayShiftEnd();
                break;
            case "sunday":
                shiftStart = roster.getSundayShiftStart();
                shiftEnd = roster.getSundayShiftEnd();
                break;
        }
        
        if (shiftStart == null || shiftStart.isEmpty() || shiftEnd == null || shiftEnd.isEmpty()) {
            return 0.0f; // Default value if shift times are not set
        }
        
        LocalTime startTime = LocalTime.parse(shiftStart);
        LocalTime endTime = LocalTime.parse(shiftEnd);
        Duration duration = Duration.between(startTime, endTime);
//        float time = duration.toMinutes() / 60.0f;
        float time = Math.round((duration.toMinutes() / 60.0f) * 100.0f) / 100.0f;
        return time;
        
    }
	public float ActualTimeCalculator(String start, String end) {
        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(end);
        Duration duration = Duration.between(startTime, endTime);
        float time = duration.toMinutes() / 60.0f;
        return time;
        
    }
	
}

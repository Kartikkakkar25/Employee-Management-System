package com.kartik.LoginProject.DTO;

import java.time.LocalDate;

public class AttendanceDTO {

	private LocalDate date;
	private String dayName;
	private String loginTime;
	private String logoutTime;
	private float scheduledHours;
	private float actualHours;
	private String status;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	public float getScheduledHours() {
		return scheduledHours;
	}
	public void setScheduledHours(float scheduledHours) {
		this.scheduledHours = scheduledHours;
	}
	public float getActualHours() {
		return actualHours;
	}
	public void setActualHours(float actualHours) {
		this.actualHours = actualHours;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

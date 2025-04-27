package com.kartik.LoginProject.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id", nullable = false)
    private User user;
	
	private String fullName;
	private String LoginTime;
	private String LogoutTime;
	private String day;
	private float ScheduledHours;
	private float ActualHours;
	@Column(nullable = false, updatable = false)
	private LocalDateTime submissionTimestamp;
	private String status;
	
	private float difference;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
	public String getLogoutTime() {
		return LogoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		LogoutTime = logoutTime;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public float getScheduledHours() {
		return ScheduledHours;
	}
	public void setScheduledHours(float scheduledHours) {
		ScheduledHours = scheduledHours;
	}
	public float getActualHours() {
		return ActualHours;
	}
	public void setActualHours(float actualHours) {
		ActualHours = actualHours;
	}
	public LocalDateTime getSubmissionTimestamp() {
		return submissionTimestamp;
	}
	public void setSubmissionTimestamp(LocalDateTime submissionTimestamp) {
		this.submissionTimestamp = submissionTimestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getDifference() {
		return difference;
	}
	public void setDifference(float difference) {
		this.difference = difference;
	}
	
	
}

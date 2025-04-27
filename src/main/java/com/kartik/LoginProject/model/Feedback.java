package com.kartik.LoginProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity

public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int SNo;
	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private int emp_id;
	private int feedback_rating=-1;
	 @Lob
	 @Column(columnDefinition = "TEXT")
	 private String feedback_text;
	 private String feedback_type;
	public int getSNo() {
		return SNo;
	}
	public void setSNo(int sNo) {
		SNo = sNo;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFeedback_rating() {
		return feedback_rating;
	}
	public void setFeedback_rating(int feedback_rating) {
		this.feedback_rating = feedback_rating;
	}
	public String getFeedback_text() {
		return feedback_text;
	}
	public void setFeedback_text(String feedback_text) {
		this.feedback_text = feedback_text;
	}
	public String getFeedback_type() {
		return feedback_type;
	}
	public void setFeedback_type(String feedback_type) {
		this.feedback_type = feedback_type;
	}
	 
	 
}

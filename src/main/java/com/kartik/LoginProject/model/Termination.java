package com.kartik.LoginProject.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Termination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id", nullable = false)
    private User user;
	private String name;
	private LocalDate termDate;
	private LocalDate lastWorkDate;
	private String termStatus;
	private String termReason;
	private String SeparationDetails;
	private String Rehire;
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getTermDate() {
		return termDate;
	}
	public void setTermDate(LocalDate termDate) {
		this.termDate = termDate;
	}
	public LocalDate getLastWorkDate() {
		return lastWorkDate;
	}
	public void setLastWorkDate(LocalDate lastWorkDate) {
		this.lastWorkDate = lastWorkDate;
	}
	public String getTermStatus() {
		return termStatus;
	}
	public void setTermStatus(String termStatus) {
		this.termStatus = termStatus;
	}
	public String getTermReason() {
		return termReason;
	}
	public void setTermReason(String termReason) {
		this.termReason = termReason;
	}
	public String getSeparationDetails() {
		return SeparationDetails;
	}
	public void setSeparationDetails(String separationDetails) {
		SeparationDetails = separationDetails;
	}
	public String getRehire() {
		return Rehire;
	}
	public void setRehire(String rehire) {
		Rehire = rehire;
	}
	
	
}

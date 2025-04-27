package com.kartik.LoginProject.model;

import jakarta.persistence.*;

@Entity
public class Roster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id", unique = true, nullable = false)
    private User user;
    private String fullName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String Status;
    
    @Column(nullable = false)
    //, columnDefinition = "VARCHAR(5) DEFAULT '00:00'"
    private String sundayShiftStart;
    @Column(nullable = false)
    private String sundayShiftEnd;
    private String mondayShiftStart;
    private String mondayShiftEnd;
    private String tuesdayShiftStart;
    private String tuesdayShiftEnd;
    private String wednesdayShiftStart;
    private String wednesdayShiftEnd;
    private String thursdayShiftStart;
    private String thursdayShiftEnd;
    private String fridayShiftStart;
    private String fridayShiftEnd;
    @Column(nullable = false)
    private String saturdayShiftStart;
    //, columnDefinition = "VARCHAR(5) DEFAULT '00:00'"
    @Column(nullable = false)
    //, columnDefinition = "VARCHAR(5) DEFAULT '00:00'"
    private String saturdayShiftEnd;
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
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSundayShiftStart() {
		return sundayShiftStart;
	}
	public void setSundayShiftStart(String sundayShiftStart) {
		this.sundayShiftStart = sundayShiftStart;
	}
	public String getSundayShiftEnd() {
		return sundayShiftEnd;
	}
	public void setSundayShiftEnd(String sundayShiftEnd) {
		this.sundayShiftEnd = sundayShiftEnd;
	}
	public String getMondayShiftStart() {
		return mondayShiftStart;
	}
	public void setMondayShiftStart(String mondayShiftStart) {
		this.mondayShiftStart = mondayShiftStart;
	}
	public String getMondayShiftEnd() {
		return mondayShiftEnd;
	}
	public void setMondayShiftEnd(String mondayShiftEnd) {
		this.mondayShiftEnd = mondayShiftEnd;
	}
	public String getTuesdayShiftStart() {
		return tuesdayShiftStart;
	}
	public void setTuesdayShiftStart(String tuesdayShiftStart) {
		this.tuesdayShiftStart = tuesdayShiftStart;
	}
	public String getTuesdayShiftEnd() {
		return tuesdayShiftEnd;
	}
	public void setTuesdayShiftEnd(String tuesdayShiftEnd) {
		this.tuesdayShiftEnd = tuesdayShiftEnd;
	}
	public String getWednesdayShiftStart() {
		return wednesdayShiftStart;
	}
	public void setWednesdayShiftStart(String wednesdayShiftStart) {
		this.wednesdayShiftStart = wednesdayShiftStart;
	}
	public String getWednesdayShiftEnd() {
		return wednesdayShiftEnd;
	}
	public void setWednesdayShiftEnd(String wednesdayShiftEnd) {
		this.wednesdayShiftEnd = wednesdayShiftEnd;
	}
	public String getThursdayShiftStart() {
		return thursdayShiftStart;
	}
	public void setThursdayShiftStart(String thursdayShiftStart) {
		this.thursdayShiftStart = thursdayShiftStart;
	}
	public String getThursdayShiftEnd() {
		return thursdayShiftEnd;
	}
	public void setThursdayShiftEnd(String thursdayShiftEnd) {
		this.thursdayShiftEnd = thursdayShiftEnd;
	}
	public String getFridayShiftStart() {
		return fridayShiftStart;
	}
	public void setFridayShiftStart(String fridayShiftStart) {
		this.fridayShiftStart = fridayShiftStart;
	}
	public String getFridayShiftEnd() {
		return fridayShiftEnd;
	}
	public void setFridayShiftEnd(String fridayShiftEnd) {
		this.fridayShiftEnd = fridayShiftEnd;
	}
	public String getSaturdayShiftStart() {
		return saturdayShiftStart;
	}
	public void setSaturdayShiftStart(String saturdayShiftStart) {
		this.saturdayShiftStart = saturdayShiftStart;
	}
	public String getSaturdayShiftEnd() {
		return saturdayShiftEnd;
	}
	public void setSaturdayShiftEnd(String saturdayShiftEnd) {
		this.saturdayShiftEnd = saturdayShiftEnd;
	}

	@Override
	public String toString() {
		return "Roster [id=" + id + ", user=" + user + ", fullName=" + fullName + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", state=" + state + ", postalCode=" + postalCode
				+ ", sundayShiftStart=" + sundayShiftStart + ", sundayShiftEnd=" + sundayShiftEnd
				+ ", mondayShiftStart=" + mondayShiftStart + ", mondayShiftEnd=" + mondayShiftEnd
				+ ", tuesdayShiftStart=" + tuesdayShiftStart + ", tuesdayShiftEnd=" + tuesdayShiftEnd
				+ ", wednesdayShiftStart=" + wednesdayShiftStart + ", wednesdayShiftEnd=" + wednesdayShiftEnd
				+ ", thursdayShiftStart=" + thursdayShiftStart + ", thursdayShiftEnd=" + thursdayShiftEnd
				+ ", fridayShiftStart=" + fridayShiftStart + ", fridayShiftEnd=" + fridayShiftEnd
				+ ", saturdayShiftStart=" + saturdayShiftStart + ", saturdayShiftEnd=" + saturdayShiftEnd + "]";
	}
}

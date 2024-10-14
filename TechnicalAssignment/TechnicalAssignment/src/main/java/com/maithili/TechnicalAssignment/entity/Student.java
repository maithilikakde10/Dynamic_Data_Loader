package com.maithili.TechnicalAssignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long rollN;
	
	private String name;
	private String schoolName;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(long rollN, String name, String schoolName) {
		super();
		this.rollN = rollN;
		this.name = name;
		this.schoolName = schoolName;
	}

	public long getRollN() {
		return rollN;
	}

	public void setRollN(long rollN) {
		this.rollN = rollN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}

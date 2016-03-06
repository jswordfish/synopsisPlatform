package com.v2tech.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
@NodeEntity
public class Vendor extends Base{
	@GraphId
	private Long id;	
	
	@Transient
	boolean tempCheck = false;
	
	String name;
	
	String address;
	
	String expertises;
	
	String email;
	
	String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExpertises() {
		return expertises;
	}

	public void setExpertises(String expertises) {
		this.expertises = expertises;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isTempCheck() {
		return tempCheck;
	}

	public void setTempCheck(boolean tempCheck) {
		this.tempCheck = tempCheck;
	}
	
	

}

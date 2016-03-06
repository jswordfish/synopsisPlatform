package com.v2tech.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
@NodeEntity
public class Project extends Base{

	@GraphId
	private Long id;	
	
	
	
	String projectName;
	
	String address;
	
	String clientOrDeveloper;
	
	String developerAddress;
	
	String phoneNo1;
	
	String phoneNo2;
	
	String phoneNo3;
	
	/**
	 * Comma Separated
	 */
	String tenderUniqueIds;
	
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getTenderUniqueIds() {
		return tenderUniqueIds;
	}

	public void setTenderUniqueIds(String tenderUniqueIds) {
		this.tenderUniqueIds = tenderUniqueIds;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientOrDeveloper() {
		return clientOrDeveloper;
	}

	public void setClientOrDeveloper(String clientOrDeveloper) {
		this.clientOrDeveloper = clientOrDeveloper;
	}

	public String getDeveloperAddress() {
		return developerAddress;
	}

	public void setDeveloperAddress(String developerAddress) {
		this.developerAddress = developerAddress;
	}

	public String getPhoneNo1() {
		return phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getPhoneNo3() {
		return phoneNo3;
	}

	public void setPhoneNo3(String phoneNo3) {
		this.phoneNo3 = phoneNo3;
	}
	
	
}

package com.v2tech.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Tender extends Base{
	
	@GraphId
	private Long id;	
	
	

	String tenderUniqueId;
	
	String tenderInitiateTime;
	
	String tenderSubmissionTime;
	
	String scopeOfWork;
	
	String tenderIssuedByName;
	
	String tenderIssuedByCompany;
	
	String tenderIssuedByDesignation;
	
	String tenderIssuedAddress;
	
	String phoneNo;
	
	String mobileNo;
	
	String email;
	
	Integer tenderFree;
	
	Integer earnestMoneyDeposit;
	
	String earnestMoneyPaymentDetails = "";
	
	String projectName;
	
	String bankName;
	
	String bankBranch;
	
	String ifscCode;
	
	String bankAddress;
	
	@Fetch
	@RelatedTo(type = "HAS_FOLLOWING", direction = Direction.INCOMING)
	Set<BidItem> items = new HashSet<BidItem>();
	
//	@Fetch
//	@RelatedTo(type = "HAS_PROPERTIES", direction = Direction.INCOMING)
//	Set<FileProperty> fileProperties = new HashSet<>();

	public String getTenderUniqueId() {
		return tenderUniqueId;
	}

	public void setTenderUniqueId(String tenderUniqueId) {
		this.tenderUniqueId = tenderUniqueId;
	}

	public String getTenderInitiateTime() {
		return tenderInitiateTime;
	}

	public void setTenderInitiateTime(String tenderInitiateTime) {
		this.tenderInitiateTime = tenderInitiateTime;
	}

	public String getTenderSubmissionTime() {
		return tenderSubmissionTime;
	}

	public void setTenderSubmissionTime(String tenderSubmissionTime) {
		this.tenderSubmissionTime = tenderSubmissionTime;
	}

	public String getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(String scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public String getTenderIssuedByName() {
		return tenderIssuedByName;
	}

	public void setTenderIssuedByName(String tenderIssuedByName) {
		this.tenderIssuedByName = tenderIssuedByName;
	}

	public String getTenderIssuedByCompany() {
		return tenderIssuedByCompany;
	}

	public void setTenderIssuedByCompany(String tenderIssuedByCompany) {
		this.tenderIssuedByCompany = tenderIssuedByCompany;
	}

	public String getTenderIssuedByDesignation() {
		return tenderIssuedByDesignation;
	}

	public void setTenderIssuedByDesignation(String tenderIssuedByDesignation) {
		this.tenderIssuedByDesignation = tenderIssuedByDesignation;
	}

	public String getTenderIssuedAddress() {
		return tenderIssuedAddress;
	}

	public void setTenderIssuedAddress(String tenderIssuedAddress) {
		this.tenderIssuedAddress = tenderIssuedAddress;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTenderFree() {
		return tenderFree;
	}

	public void setTenderFree(Integer tenderFree) {
		this.tenderFree = tenderFree;
	}

	public Integer getEarnestMoneyDeposit() {
		return earnestMoneyDeposit;
	}

	public void setEarnestMoneyDeposit(Integer earnestMoneyDeposit) {
		this.earnestMoneyDeposit = earnestMoneyDeposit;
	}

	public String getEarnestMoneyPaymentDetails() {
		return earnestMoneyPaymentDetails;
	}

	public void setEarnestMoneyPaymentDetails(String earnestMoneyPaymentDetails) {
		this.earnestMoneyPaymentDetails = earnestMoneyPaymentDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Set<BidItem> getItems() {
		return items;
	}

	public void setItems(Set<BidItem> items) {
		this.items = items;
	}

	

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
	
	
}

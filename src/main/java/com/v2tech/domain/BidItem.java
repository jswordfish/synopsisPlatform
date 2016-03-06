package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class BidItem extends Base{
	
	@GraphId
	private Long id;
	
	private Long tenderBidItemId;
	
	private Boolean byVendor = false;
	
	private String vendorName;
	
	String unit;
	
	String vendorComment;
	
	Float rate = 0f;
	
	Integer totalQuantity = 0;
	
	Double amount = 0d;
	
	String itemDescription;
	
	Float serial;
	
	Double inputRate = 0d;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVendorComment() {
		return vendorComment;
	}

	public void setVendorComment(String vendorComment) {
		this.vendorComment = vendorComment;
	}

	public Float getRate() {
		if(this.rate == null){
			this.rate = 0f;
		}
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Integer getTotalQuantity() {
		if(this.totalQuantity == null){
			this.totalQuantity = 0;
		}
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getAmount() {
		if(this.amount == null){
			this.amount = 0d;
		}
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Float getSerial() {
		return serial;
	}

	public void setSerial(Float serial) {
		this.serial = serial;
	}

	public Double getInputRate() {
		return inputRate;
	}

	public void setInputRate(Double inputRate) {
		this.inputRate = inputRate;
	}

	public Long getTenderBidItemId() {
		return tenderBidItemId;
	}

	public void setTenderBidItemId(Long tenderBidItemId) {
		this.tenderBidItemId = tenderBidItemId;
	}

	public Boolean getByVendor() {
		return byVendor;
	}

	public void setByVendor(Boolean byVendor) {
		this.byVendor = byVendor;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	
	
}

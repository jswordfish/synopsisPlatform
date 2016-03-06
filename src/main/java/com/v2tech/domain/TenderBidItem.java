package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
@NodeEntity
public class TenderBidItem implements Comparable<TenderBidItem>{
	@GraphId
	private Long id;
	
	String unit;
	
	Integer totalQuantity;
	
	String itemDescription;
	
	String tenderUniqueNo;
	
	Double inputRate;
	
	Float serial;

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

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getTenderUniqueNo() {
		return tenderUniqueNo;
	}

	public void setTenderUniqueNo(String tenderUniqueNo) {
		this.tenderUniqueNo = tenderUniqueNo;
	}

	public Double getInputRate() {
		return inputRate;
	}

	public void setInputRate(Double inputRate) {
		this.inputRate = inputRate;
	}

	public Float getSerial() {
		return serial;
	}

	public void setSerial(Float serial) {
		this.serial = serial;
	}

	@Override
	public int compareTo(TenderBidItem o) {
		// TODO Auto-generated method stub
		return this.serial.compareTo(o.getSerial());
	}
	
	
}

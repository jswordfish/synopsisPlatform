package com.v2tech.domain;

import java.util.ArrayList;
import java.util.List;

public class CompleteComparativeAnalysisForTenderItem {
	
	String unit;
	
	Integer totalQuantity;
	
	String itemDescription;
	
	String tenderUniqueNo;
	
	Double inputRate;
	
	Float serial;
	
	Float maxMinRatio;
	
	Float minimumRate;
	
	String vendorNameWithMinimumRate;
	
	Float maximumRate;
	
	String vendorNameWithMaximumRate;
	
	List<BidItem> bidItems = new ArrayList<>();

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

	public Float getMaxMinRatio() {
		return maxMinRatio;
	}

	public void setMaxMinRatio(Float maxMinRatio) {
		this.maxMinRatio = maxMinRatio;
	}

	

	public String getVendorNameWithMinimumRate() {
		return vendorNameWithMinimumRate;
	}

	public void setVendorNameWithMinimumRate(String vendorNameWithMinimumRate) {
		this.vendorNameWithMinimumRate = vendorNameWithMinimumRate;
	}

	

	public Float getMinimumRate() {
		return minimumRate;
	}

	public void setMinimumRate(Float minimumRate) {
		this.minimumRate = minimumRate;
	}

	public Float getMaximumRate() {
		return maximumRate;
	}

	public void setMaximumRate(Float maximumRate) {
		this.maximumRate = maximumRate;
	}

	public String getVendorNameWithMaximumRate() {
		return vendorNameWithMaximumRate;
	}

	public void setVendorNameWithMaximumRate(String vendorNameWithMaximumRate) {
		this.vendorNameWithMaximumRate = vendorNameWithMaximumRate;
	}

	public List<BidItem> getBidItems() {
		return bidItems;
	}

	public void setBidItems(List<BidItem> bidItems) {
		this.bidItems = bidItems;
	}
	
	

}

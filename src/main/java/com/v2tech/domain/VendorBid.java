package com.v2tech.domain;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * @author jsutaria
 *
 */
@NodeEntity
public class VendorBid extends Base{
	@GraphId
	private Long id;
	
	
	
	String vendorName;
	
	String tenderUniqueNo;
	
	@Fetch
	@RelatedTo(type = "CORRESPONDS_TO")
	Tender tender;
	
	
	@Fetch
	@RelatedTo(type = "HAS_FOLLOWING", direction = Direction.INCOMING)
	Set<BidItem> bidItems = new HashSet<BidItem>();
	
	@org.springframework.data.annotation.Transient
	private Double totalAmountQuoted = 0d;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	


	public Tender getTender() {
		return tender;
	}


	public void setTender(Tender tender) {
		this.tender = tender;
	}


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	


	


	public Set<BidItem> getBidItems() {
		return bidItems;
	}


	public void setBidItems(Set<BidItem> bidItems) {
		this.bidItems = bidItems;
	}


	public String getTenderUniqueNo() {
		return tenderUniqueNo;
	}


	public void setTenderUniqueNo(String tenderUniqueNo) {
		this.tenderUniqueNo = tenderUniqueNo;
	}


	public Double getTotalAmountQuoted() {
		return totalAmountQuoted;
	}


	public void setTotalAmountQuoted(Double totalAmountQuoted) {
		this.totalAmountQuoted = totalAmountQuoted;
	}
	
	
	
	
}

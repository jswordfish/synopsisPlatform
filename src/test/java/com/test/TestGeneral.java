package com.test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.v2tech.domain.BidItem;
import com.v2tech.domain.Project;
import com.v2tech.domain.Tender;
import com.v2tech.domain.Vendor;
import com.v2tech.domain.VendorBid;
import com.v2tech.repository.BidItemRepository;
import com.v2tech.services.ProjectService;
import com.v2tech.services.TenderService;
import com.v2tech.services.VendorBidService;
import com.v2tech.services.VendorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContextEmbedded.xml"})
@Transactional("neo4jTransactionManager")
public class TestGeneral {
	@Autowired
	ProjectService projectService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	TenderService tenderService;
	
	@Autowired
	VendorBidService vendorBidService;
	@Autowired
	BidItemRepository itemRep;
	
	
	@Test
	public void testCreateUpdateProject(){
		Project proj = new Project();
		proj.setAddress("eeee");
		proj.setClientOrDeveloper("client 1");
		proj.setProjectName("Matru Krupa");
		proj.setTenantId("Tenant1");
		proj.setTenderUniqueIds(proj.getProjectName()+"-"+Calendar.getInstance().getTimeInMillis());
		projectService.saveOrUpdateProject(proj);;
	}
	
	@Test
	public void testCreateUpdateVendor(){
		Vendor vendor = new Vendor();
		vendor.setAddress("rferere");
		vendor.setExpertises("Plumbing");
		vendor.setName("Vendor2");
		vendor.setTenantId("Tenant1");
		vendorService.saveOrUpdateVendor(vendor);
	}
	
	@Test
	public void testDeleteTender(){
		tenderService.removeTender(5l);
	}
	
	@Test
	public void testCreateUpdateTender(){
		Tender tender = new Tender();
		tender.setEmail("jat@ert.com");
		tender.setProjectName("Nirlon Complex");
		tender.setTenantId("Tenant1");
		tender.setTenderUniqueId(tender.getProjectName()+"-1450444236988");
		BidItem bidItem = new BidItem();
		bidItem.setItemDescription("Desc1");
		bidItem.setTotalQuantity(45);
		bidItem.setUnit("cum");
		
		BidItem bidItem2 = new BidItem();
		bidItem2.setItemDescription("Desc2");
		bidItem2.setTotalQuantity(45);
		bidItem2.setUnit("met");
		
		Set<BidItem> items = new HashSet<BidItem>();
		items.add(bidItem);
		items.add(bidItem2);
		tender.setItems(items);
		
		tenderService.saveOrUpdateTender(tender);
	}
	
	@Test
	public void testCreateUpdateVendorBid(){
		VendorBid vendorBid = new VendorBid();
		vendorBid.setTenantId("Tenant1");
		vendorBid.setTenderUniqueNo("Matru Krupa-1450444236988");
		vendorBid.setVendorName("Vendor2");
		Tender tender = new Tender();
		tender.setTenderUniqueId("Matru Krupa-1450444236988");
		vendorBid.setTender(tender);
		BidItem bidItem = new BidItem();
		bidItem.setItemDescription("Desc1");
		bidItem.setTotalQuantity(45);
		bidItem.setUnit("cum");
		
		BidItem bidItem2 = new BidItem();
		bidItem2.setItemDescription("Desc2");
		bidItem2.setTotalQuantity(45);
		bidItem2.setUnit("met");
		
		Set<BidItem> items = new HashSet<BidItem>();
		items.add(bidItem);
		items.add(bidItem2);
		
		vendorBid.setBidItems(items);
		vendorBidService.createVendorBid(vendorBid);
	}

}

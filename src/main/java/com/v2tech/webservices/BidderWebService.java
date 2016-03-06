package com.v2tech.webservices;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.BidItem;
import com.v2tech.domain.CompleteComparativeAnalysisForTenderItem;
import com.v2tech.domain.FileProperty;
import com.v2tech.domain.Project;
import com.v2tech.domain.Tender;
import com.v2tech.domain.TenderBidItem;
import com.v2tech.domain.User;
import com.v2tech.domain.Vendor;
import com.v2tech.domain.VendorBid;
import com.v2tech.repository.BidItemRepository;
import com.v2tech.repository.FilePropertyRepository;
import com.v2tech.repository.TenderBidItemRepository;
import com.v2tech.repository.UserRepository;
import com.v2tech.repository.VendorBidRepository;
import com.v2tech.services.ProjectService;
import com.v2tech.services.TenderService;
import com.v2tech.services.UserService;
import com.v2tech.services.VendorBidService;
import com.v2tech.services.VendorService;
import com.v2tech.util.EmailThread;
import com.v2tech.util.UtilService;
@Path("/bidderService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class BidderWebService {
	@Autowired
	TenderService tenderService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	VendorBidService vendorBidService;
	
	@Autowired
	VendorBidRepository vendorBidRepository;
	
	@Autowired
	FilePropertyRepository filePropertyRepository;
	
	@Autowired
	TenderBidItemRepository tenderBidItemRepository;
	
	@Autowired
	BidItemRepository bidItemRepository;
	
	String testTenantId = "LodhaAndCo";
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@POST
    @Path("/login/user/{user}/password/{password}/tenantId/{tenantId}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response authenticate(@PathParam("user") String user, @PathParam("password") String password){
		try {
			User usr = userService.login(user, password);
			return Response.ok().entity(usr).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e).build();
		}
		
	}
	
	@POST
    @Path("/uploadFileForTender/tenderUniqueNo/{tenderUniqueNo}/fileDesc/{fileDesc}/tenantId/{tenantId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response uploadFileForTender(Attachment attachment, @PathParam("tenderUniqueNo") String tenderUniqueNo, @PathParam("fileDesc") String fileDesc, @PathParam("tenantId") String tenantId) {
		try {
			DataHandler handler = attachment.getDataHandler();
			
			InputStream stream = handler.getInputStream();
			
			MultivaluedMap<String, String> map = attachment.getHeaders();
			String fileName = UtilService.getFileName(map);
			tenderService.addFileToTender(tenantId, tenderUniqueNo, fileName, fileDesc, handler);
			return Response.ok().build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).tag("CAN_NOT_UPLOAD").build();
		}
       
	}
	
	@POST
    @Path("/addFileLinkForTender/tenantId/{tenantId}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response addFileLinkForTender(FileProperty fileProperty, @PathParam("tenantId") String tenantId) {
		try {
			
			
			filePropertyRepository.save(fileProperty);
			return Response.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).tag("CAN_NOT_ADD_FILELINK").build();
		}
       
	}
	
	@POST
    @Path("/deleteFileForTender/fileId/{fileId}")
    @Produces("application/json")
	public Response deleteFileForTender(@PathParam("fileId") String fileId){
		try{
			filePropertyRepository.delete(Long.valueOf(fileId));
			return Response.ok().build();
		}
		catch(NumberFormatException e){
			return Response.status(Status.BAD_REQUEST).tag("Error:InvalidFileId").build();
		}
	}
	
	@GET
    @Path("/listFileItems/tenderUniqueNo/{tenderUniqueNo}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<FileProperty> listAllFileItemsForTender(@PathParam("tenderUniqueNo") String tenderUniqueNo, @PathParam("token") String token){
		return filePropertyRepository.findFilePropertiesByTenderUniqueNo(tenderUniqueNo);
	}
	
	@GET
    @Path("/listTenderBidItems/tenderUniqueNo/{tenderUniqueNo}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TenderBidItem> listAllTenderBidItemsForTender(@PathParam("tenderUniqueNo") String tenderUniqueNo, @PathParam("token") String token){
		List<TenderBidItem> bidItems = new ArrayList<TenderBidItem>(tenderBidItemRepository.findTenderBidItemsByTenderUniqueNo(tenderUniqueNo));
		Collections.sort(bidItems);
		return bidItems;
	}
	
	@POST
    @Path("/deleteTenderBidItemForTender/tenderBidItemId/{tenderBidItemId}")
    @Produces("application/json")
	public Response deleteTenderBidItemForTender(@PathParam("tenderBidItemId") String tenderBidItemId){
		try{
			tenderBidItemRepository.delete(Long.valueOf(tenderBidItemId));
			return Response.ok().build();
		}
		catch(NumberFormatException e){
			return Response.status(Status.BAD_REQUEST).tag("Error:InvalidFileId").build();
		}
	}
	
	@POST
    @Path("/addOrUpdateTenderBidItemForTender/tenantId/{tenantId}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response addOrUpdateTenderBidItemToTender(TenderBidItem tenderBidItem, @PathParam("tenantId") String tenantId) {
		try {
			if(tenderBidItem.getId() == null){
				tenderBidItemRepository.save(tenderBidItem);
			}
			else{
				TenderBidItem tId = tenderBidItemRepository.findOne(tenderBidItem.getId());
				Mapper mapper = new DozerBeanMapper();
				mapper.map(tenderBidItem, tId);
				tenderBidItemRepository.save(tId);
			}
			
			return Response.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).tag("CAN_NOT_CREATE_BID_ITEM").build();
		}
       
	}
	
	
	@POST
    @Path("/createUpdateVendor/token/{token}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public void createUpdateVendor(Vendor vendor, @PathParam("token") String token) {
		if(vendor.getTenantId() == null || vendor.getTenantId().trim().length() == 0){
			vendor.setTenantId(testTenantId);
		}
		vendorService.saveOrUpdateVendor(vendor);
	}
	
	@POST
    @Path("/createUpdateProject/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public void createUpdateProject(Project project, @PathParam("token") String token) {
		if(project.getTenantId() == null || project.getTenantId().trim().length() == 0){
			project.setTenantId(testTenantId);
		}
		projectService.saveOrUpdateProject(project);
	}
	
	@POST
    @Path("/createUpdateTender/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public String createUpdateTender(Tender tender, @PathParam("token") String token) {
		if(tender.getTenantId() == null || tender.getTenantId().trim().length() == 0){
			tender.setTenantId(testTenantId);
		}
		return tenderService.saveOrUpdateTender(tender);
	}
	
	
	@GET
    @Path("/listVendorBidsForTender/tenderUniqueNo/{tenderUniqueNo}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<VendorBid> getAllVendorBidsForTenderNo(@PathParam("tenderUniqueNo") String tenderUniqueNo, @PathParam("token") String token){
		Set<VendorBid> bids = vendorBidRepository.findAllVendorBidsForTender(tenderUniqueNo);
		
		return bids;
	}
	
	@GET
    @Path("/listCompleteAnalysisForTender/tenderUniqueNo/{tenderUniqueNo}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CompleteComparativeAnalysisForTenderItem> computeCompleteBidAnalysis(@PathParam("tenderUniqueNo") String tenderUniqueNo, @PathParam("token") String token){
		//Set<VendorBid> bids = getAllVendorBidsForTenderNo(tenderUniqueNo, token);
		Set<TenderBidItem> itemsForTender = tenderBidItemRepository.findTenderBidItemsByTenderUniqueNo(tenderUniqueNo);
		List<CompleteComparativeAnalysisForTenderItem> compItems = new ArrayList<>();
		for(TenderBidItem item : itemsForTender){
			CompleteComparativeAnalysisForTenderItem reportItem = new CompleteComparativeAnalysisForTenderItem();
			reportItem.setInputRate(item.getInputRate());
			reportItem.setItemDescription(item.getItemDescription());
			reportItem.setUnit(item.getUnit());
			reportItem.setSerial(item.getSerial());
			reportItem.setTenderUniqueNo(tenderUniqueNo);
			reportItem.setTotalQuantity(item.getTotalQuantity());
			Set<BidItem> items = bidItemRepository.findVendorBidItemsForItemId(item.getId());
			BidItem min = getMinRateBidItem(items);
				if(min == null){
					reportItem.setMinimumRate(0f);
					reportItem.setVendorNameWithMinimumRate("NA");
				}
				else{
					reportItem.setMinimumRate(min.getRate());
					reportItem.setVendorNameWithMinimumRate(min.getVendorName());
				}
				
			BidItem max = getMaxRateBidItem(items);
				if(max == null){
					reportItem.setMaximumRate(0f);
					reportItem.setVendorNameWithMaximumRate("NA");
				}
				else{
					reportItem.setMaximumRate(max.getRate());
					reportItem.setVendorNameWithMaximumRate(max.getVendorName());
				}
				
				if(min != null && max != null){
					System.out.println(" in is "+min.getRate()+" max is "+max.getRate());
					if(min.getRate() != 0f){
						BigDecimal bd = new BigDecimal(Float.toString(max.getRate() / min.getRate()));
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						reportItem.setMaxMinRatio( bd.floatValue());
					}
					else{
						reportItem.setMaxMinRatio( -1f);
					}
					
				}
				else{
					reportItem.setMaxMinRatio( 1.0f);
				}
				
			reportItem.setBidItems(new ArrayList(items));
			compItems.add(reportItem);
		}
		return compItems;
	}
	
	private BidItem getMinRateBidItem(Set<BidItem> items){
		Float minRate = 0f;
		BidItem minBid = null;
		int count = 0;
		for(BidItem item: items){
			if(minRate == 0f){
				minRate = item.getRate();
				minBid = item;
			}
			count++;
			System.out.println("item is "+item);
			System.out.println("item.getRate is "+item.getRate());
			System.out.println("minrate is "+minRate);
			if(item.getRate()!= 0f && item.getRate() <= minRate){
				minRate = item.getRate();
				minBid = item;
			}
			
			if(count == items.size() -1 && minRate == 0.f){
				minRate = item.getRate();
			}
		}
		System.out.println("final minrate is "+minRate);
		return minBid;
	}
	
	private BidItem getMaxRateBidItem(Set<BidItem> items){
		Float maxRate = 0f;
		BidItem maxBid = null;
		for(BidItem item: items){
			if(item.getRate() > maxRate){
				maxRate = item.getRate();
				maxBid = item;
			}
		}
		return maxBid;
	}
	
	@GET
    @Path("/listTendersVendorsCanBidFor/vendorName/{vendorName}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Tender> getTendersVendorCanBidFor(@PathParam("vendorName") String vendorName, @PathParam("token") String token){
		Set<VendorBid> bids = vendorBidRepository.findVendorBidsForVendor(vendorName);
		Set<Tender> tenders = new HashSet<>();
			for(VendorBid bid : bids){
				tenders.add(bid.getTender());
			}
		return tenders;
	}
	
	@GET
    @Path("/getVendorBid/vendorName/{vendorName}/tenderNo/{tenderNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public VendorBid getVendorBidByVendorNameAndTenderNo(@PathParam("vendorName") String vendorName, @PathParam("tenderNo") String tenderNo){
		VendorBid vendorBid = vendorBidService.findVendorBidByVendorNameAndTendorUniqueNo(vendorName, tenderNo);
		return vendorBid;
	}
	
	@GET
    @Path("/getVendorBids/vendorNames/{vendorNames}/tenderNo/{tenderNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVendorBidsByVendorNameAndTenderNo(@PathParam("vendorNames") String vendorNames, @PathParam("tenderNo") String tenderNo){
		if(vendorNames == null){
			return Response.status(Status.BAD_REQUEST).build();
		}
		StringTokenizer stk = new StringTokenizer(vendorNames, ",");
		List vendorBids = new ArrayList<VendorBid>();
			while(stk.hasMoreTokens()){
				String name = stk.nextToken();
				VendorBid vendorBid = vendorBidService.findVendorBidByVendorNameAndTendorUniqueNo(name, tenderNo);
				for(BidItem item : vendorBid.getBidItems()){
					vendorBid.setTotalAmountQuoted(vendorBid.getTotalAmountQuoted()+item.getAmount());
				}
				vendorBid.setBidItems(new HashSet<BidItem>());
				vendorBids.add(vendorBid);
			}
		return Response.ok(vendorBids).build();
	
	}
	
	private void saveVendorBids(List<Vendor> createVendorsBid, String tenderNo){
		Tender tender = tenderService.findTenderByUniqueId(tenderNo);
		Set<TenderBidItem> items = tenderBidItemRepository.findTenderBidItemsByTenderUniqueNo(tenderNo);
		
		for(Vendor v : createVendorsBid){
			//vendorBidService
			VendorBid vendorBid = new VendorBid();
			vendorBid.setTenantId(testTenantId);
			vendorBid.setTenderUniqueNo(tenderNo);
			vendorBid.setVendorName(v.getName());
			for(TenderBidItem item : items){
				BidItem bidItem = new BidItem();
				bidItem.setTenderBidItemId(item.getId());
				bidItem.setByVendor(true);
				bidItem.setVendorName(v.getName());
				bidItem.setItemDescription(item.getItemDescription());
				bidItem.setTenantId(testTenantId);
				bidItem.setUnit(item.getUnit());
				bidItem.setTotalQuantity(item.getTotalQuantity());
				bidItem.setSerial(item.getSerial());
				vendorBid.getBidItems().add(bidItem);
			}
			vendorBidService.createVendorBid(vendorBid);
		}
	}
	
	@POST
    @Path("/inviteVendorsForTender/tenderNo/{tenderNo}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response inviteVendorsToFillTender(Set<Vendor> vendors, @PathParam("tenderNo") String tenderNo, @PathParam("token") String token){
		List<String> emails = new ArrayList();
		List<Vendor> createVendorsBid = new ArrayList<>();
		for(Vendor ven : vendors){
			if(ven.isTempCheck()){
				emails.add(ven.getEmail());
				createVendorsBid.add(ven);
			}
		}
		
		/**
		 * Step - Create VendorBidItems
		 */
		saveVendorBids(createVendorsBid, tenderNo);
		
		String sendTos[] = new String[emails.size()];
		sendTos = emails.toArray(sendTos);
		Tender tender = tenderService.findTenderByUniqueId(tenderNo);
		
		Set<FileProperty> files = filePropertyRepository.findFilePropertiesByTenderUniqueNo(tenderNo);
		String links = "";
		for(FileProperty fileProperty: files){
			links += fileProperty.getFileURL() +"\r\n ";
		}
		
			if(tender == null){
				return Response.status(Status.BAD_REQUEST).tag("INVALID_TENDOR_NUMBER").build();
			}
		EmailThread emailThread = new EmailThread(tender, sendTos, links);
		Thread thread = new Thread(emailThread);
		thread.start();
		return Response.ok().build();
	}
	
	
	@POST
    @Path("/createVendorBid/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public void createVendorBid(VendorBid vendorBid, @PathParam("token") String token) {
		if(vendorBid.getTenantId() == null || vendorBid.getTenantId().trim().length() == 0){
			vendorBid.setTenantId(testTenantId);
		}
		vendorBidService.createVendorBid(vendorBid);
	}
	
	@POST
    @Path("/updateBidItemVendorBid/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public void updateBidItemVendorBid( BidItem bidItem, @PathParam("token") String token) {
		if(bidItem.getTenantId() == null || bidItem.getTenantId().trim().length() == 0){
			bidItem.setTenantId(testTenantId);
		}
		vendorBidService.updateVendorBidItem(bidItem);
	}
	
	@GET
    @Path("/vendors/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Vendor> getVendors(@PathParam("token") String token){
		Set<Vendor> vs = vendorService.getAllVendors();
		return vs;
	}
	
	@GET
    @Path("/vendor/email/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vendor getVendorByEmail(@PathParam("email") String email){
		Vendor vendor = vendorService.findVendorByEmail(email);
		return vendor;
	}
	
	
	@GET
    @Path("/projects/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Project> getProjects(@PathParam("token") String token){
		Set<Project> vs = projectService.getAllProjects();
		return vs;
	}
	
	@GET
    @Path("/listProjectNames/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String[] listProjectNames(@PathParam("token") String token){
		return projectService.listProjectNames();
		
	}
	
	@GET
    @Path("/tenders/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Tender> getTenders(@PathParam("token") String token){
		Set<Tender> vs = tenderService.getAllTenders();
		return vs;
	}
	
	
	@GET
    @Path("/users/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<User> getUsers(@PathParam("token") String token){
		org.springframework.data.neo4j.conversion.Result<User> users = userRepository.findAll();
		
		Iterator<User> itr = users.iterator();
		 Set<User> us = new HashSet();
		 while(itr.hasNext()){
			 us.add(itr.next());
		 }
		return us;
	}
	
	
	@POST
    @Path("/createUpdateUser/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public void createUpdateUser(User user, @PathParam("token") String token) {
		if(user.getTenantId() == null || user.getTenantId().trim().length() == 0){
			user.setTenantId(testTenantId);
		}
		userService.createOrUpdateUser(user);
	}
	
	@POST
    @Path("/deleteUser/userName/{userName}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUser(@PathParam("userName") String userName, @PathParam("token") String token){
		userService.deleteUser(userName);
	}
	
	@POST
    @Path("/deleteVendor/vendorName/{vendorName}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteVendor(@PathParam("vendorName") String vendorName, @PathParam("token") String token){
		vendorService.deleteVendor(vendorName);
	}
	
	@POST
    @Path("/deleteProject/projectName/{projectName}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProject(@PathParam("projectName") String projectName, @PathParam("token") String token){
		projectService.deleteProject(projectName);
	}
	
	@POST
    @Path("/deleteTender/tenderUniqueNo/{tenderUniqueNo}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTender(@PathParam("tenderUniqueNo") String tenderUniqueNo, @PathParam("token") String token){
		tenderService.deleteTender(tenderUniqueNo);
		/**
		 * Also delete all the vendor bids associated with the tender.
		 */
		Set<VendorBid> bids = vendorBidRepository.findAllVendorBidsForTender(tenderUniqueNo);
		for(VendorBid bid : bids){
			vendorBidRepository.delete(bid.getId());
		}
		
	}

}

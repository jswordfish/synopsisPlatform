package com.v2tech.services;

import java.util.Set;



import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.BidItem;
import com.v2tech.domain.Tender;
import com.v2tech.domain.VendorBid;
import com.v2tech.repository.BidItemRepository;
import com.v2tech.repository.VendorBidRepository;

@Service
public class VendorBidService extends BaseService {
	@Autowired
	VendorBidRepository vendorBidRepository;
	
	@Autowired
	TenderService tenderService;
	
	@Autowired
	BidItemRepository bidItemRepository;
	
	public VendorBid findVendorBidByVendorNameAndTendorUniqueNo(String vendorName, String tenderUniqueNo){
		Set<VendorBid> bids = vendorBidRepository.findVendorBidByName(vendorName, tenderUniqueNo);
		if(bids.size() == 0){
			return null;
		}
		else if(bids.size() > 1){
			throw new V2GenericException("More than 1 bid exists");
		}
		else{
			VendorBid[] bds = new VendorBid[bids.size()];
			bds = bids.toArray(bds);
			return bds[0];
		}
	}
	
	public void createVendorBid(VendorBid vendorBid){
		validate(vendorBid);
		VendorBid bid = findVendorBidByVendorNameAndTendorUniqueNo(vendorBid.getVendorName(), vendorBid.getTenderUniqueNo());
		//resolve tender
		Tender tender = tenderService.findTenderByUniqueId(vendorBid.getTenderUniqueNo());
		if(bid == null){
			//create mode
			bid = vendorBid;
			
			
			if(tender == null){
				throw new V2GenericException("Tender can not be null");
			}
			
			bid.setTender(tender);
			vendorBidRepository.save(bid);
		}
		else{
			//update mode
			//step 1 - delete all the bid items existing earlier
			for(BidItem bidItem : bid.getBidItems()){
				bidItemRepository.delete(bidItem);
			}
			if(tender == null){
				throw new V2GenericException("Tender can not be null");
			}
			
			bid.setTender(tender);
			bid.setBidItems(vendorBid.getBidItems());
			
			//step 2 - save Vendor bid
			vendorBidRepository.save(bid);
		}
		
	}
	
	public void updateVendorBidItem(BidItem bidItem){
		BidItem bidItem1 = bidItemRepository.findOne(bidItem.getId());
		
		if(bidItem1 == null){
			throw new V2GenericException("BidItem does not exist");
		}
		
		Mapper  mapper = new DozerBeanMapper();
		
		mapper.map(bidItem, bidItem1);
		bidItemRepository.save(bidItem1);
		
	}
	
	

}

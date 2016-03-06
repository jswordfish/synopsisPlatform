package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.VendorBid;
@Repository
public interface VendorBidRepository extends GraphRepository<VendorBid> {
	
	@Query("MATCH (vBid:VendorBid) WHERE vBid.vendorName =~ {0} AND vBid.tenderUniqueNo =~ {1} return vBid;")
	public Set<VendorBid> findVendorBidByName(String vendorName, String tenderUniqueNo);
	
	@Query("MATCH (vBid:VendorBid) WHERE vBid.vendorName =~ {0} return vBid;")
	public Set<VendorBid> findVendorBidsForVendor(String vendorName);
	

	@Query("MATCH (vBid:VendorBid) WHERE vBid.tenderUniqueNo =~ {0} return vBid;")
	public Set<VendorBid> findAllVendorBidsForTender(String tenderUniqeNo);

}

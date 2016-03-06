package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.BidItem;
import com.v2tech.domain.Tender;
@Repository
public interface BidItemRepository extends GraphRepository<BidItem>{

	@Query("MATCH (item:BidItem) WHERE item.tenderBidItemId = {0} AND item.byVendor = true return item;")
	public Set<BidItem> findVendorBidItemsForItemId(Long tenderBidItemId);
}

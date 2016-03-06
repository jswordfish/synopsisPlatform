package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.TenderBidItem;
@Repository
public interface TenderBidItemRepository extends GraphRepository<TenderBidItem>{
	
	@Query("MATCH (tBI:TenderBidItem) WHERE tBI.tenderUniqueNo =~ {0} return tBI;")
	public Set<TenderBidItem> findTenderBidItemsByTenderUniqueNo(String tenderUniqueNo);

}


package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Tender;

@Repository
public interface TenderRepository  extends GraphRepository<Tender>{
	
	
	@Query("MATCH (tr:Tender) WHERE tr.tenderUniqueId =~ {0} return tr;")
	public Set<Tender> findTenderByUniqueId(String tenderUniqueId);

}

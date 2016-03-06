package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.Vendor;

public interface VendorRepository extends GraphRepository<Vendor>{

	@Query("MATCH (vr:Vendor) WHERE vr.name = {0} return vr;")
	public Set<Vendor> findVendorByName(String vendorName);
	
	@Query("MATCH (vr:Vendor) WHERE vr.email = {0} return vr;")
	public Set<Vendor> findVendorByEmail(String email);
}

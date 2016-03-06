package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.FileProperty;
import com.v2tech.domain.Vendor;
@Repository
public interface FilePropertyRepository extends GraphRepository<FileProperty>{
	
	@Query("MATCH (fp:FileProperty) WHERE fp.tenderUniqueId =~ {0} return fp;")
	public Set<FileProperty> findFilePropertiesByTenderUniqueNo(String tenderUniqueNo);

}

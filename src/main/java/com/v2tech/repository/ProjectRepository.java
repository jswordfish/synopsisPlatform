package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.Project;

public interface ProjectRepository extends GraphRepository<Project>{

	@Query("MATCH (pr:Project) WHERE pr.projectName =~ {0} return pr;")
	public Set<Project> findProjectByName(String projectName);

	
}

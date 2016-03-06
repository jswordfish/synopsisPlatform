package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.User;
@Repository
public interface UserRepository extends GraphRepository<User>{

	@Query("MATCH (us:User) WHERE us.userEmail =~ {0} return us;")
	public Set<User> findUserByEmail(String userEmail);
	
	
	@Query("MATCH (us:User) WHERE us.userEmail = {0} AND us.password ={1} return us;")
	public Set<User> findUserByEmailAndPassword(String userEmail, String password);
}

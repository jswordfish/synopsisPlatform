package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2tech.domain.User;
import com.v2tech.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContextEmbedded.xml"})
@Transactional("neo4jTransactionManager")
public class TestUser {
	@Autowired
	UserService userService;
	
	@Test
	public void testUserCreation() throws JsonProcessingException{
		User user = new User();
		user.setUserEmail("jatinsut@yahoo.com");
		user.setPassword("secret");
		user.setUserType("Admin");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(user));
		//userService.createOrUpdateUser(user);
		
//		user = new User();
//		user.setUserEmail("jatin.sutaria@gmail.com");
//		
//		user.setPassword("secret");
//		user.setUserType("Vendor");
//		userService.createOrUpdateUser(user);
		
	}
	
	@Test
	public void testUserLogin(){
		User user = userService.login("jatinsut@yahoo.com", "secret");
		
		User user1 = userService.login("vendor1@yahoo.com", "secret");
		System.out.println("here");
	}

}

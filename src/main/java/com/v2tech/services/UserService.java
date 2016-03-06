package com.v2tech.services;

import java.util.Set;



import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.User;
import com.v2tech.domain.Vendor;
import com.v2tech.repository.UserRepository;
@Service

public class UserService extends BaseService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VendorService vendorService;
	
	public User getUserByEmail(String email){
		Set<User> users = userRepository.findUserByEmail(email);
		if(users.size() == 0){
			return null;
		}
		else if(users.size() > 1){
			throw new V2GenericException("More than 1 users with same email");
		}
		else{
			User us[] = new User[users.size()];
			us = users.toArray(us);
			return us[0];
		}
	}
	
	
	
	public void createOrUpdateUser(User user){
		if(user.getUserEmail() == null || user.getUserEmail().trim().length() == 0){
			throw new V2GenericException("No email present");
		}
		
		if(user.getPassword() == null || user.getPassword().trim().length() == 0){
			throw new V2GenericException("No password present");
		}
		
		User usr = getUserByEmail(user.getUserEmail());
			if(usr == null){
				//create mode
				usr = user;
			}
			else{
				//update mode
				user.setId(usr.getId());
				Mapper mapper = new DozerBeanMapper();
				mapper.map(user, usr);
				
			}
			userRepository.save(usr);
	}
	
	
	public User login(String user, String password){
		User usr = getUserByEmail(user);
		
		if(usr == null){
			//It could be a vendor
			Vendor ven = vendorService.findVendorByEmail(user);
			if(ven == null){
				return null;
			}
			else{
				if(ven.getPassword().equals(password)){
					User us = new User();
					us.setUserType("Vendor");
					us.setUserEmail(ven.getEmail());
					us.setPassword(ven.getPassword());
					us.setTenantId(ven.getTenantId());
					return us;
				}
				else{
					return null;
				}
				
			}
		}
		
		if(usr.getPassword().equals(password)){
			return usr;
		}
		else{
			return null;
		}
	}
	
	
	public void deleteUser(String userName){
		User userTodel = getUserByEmail(userName);
			if(userTodel!= null){
				userRepository.delete(userTodel.getId());
			}
		
	}

}

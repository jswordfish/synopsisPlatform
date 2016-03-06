package com.v2tech.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Vendor;
import com.v2tech.repository.VendorRepository;

@Service
public class VendorService extends BaseService{

	@Autowired
	VendorRepository vendorRepository;
	
	public Set<Vendor> getAllVendors(){
		 org.springframework.data.neo4j.conversion.Result<Vendor> vens = vendorRepository.findAll();
		 Iterator<Vendor> itr = vens.iterator();
		 Set<Vendor> vendors = new HashSet();
		 while(itr.hasNext()){
			 vendors.add(itr.next());
		 }
		 return vendors;
	}
	
	public void deleteVendor(String name){
		Vendor delVendor = findVendorByName(name);
			if(delVendor != null){
				vendorRepository.delete(delVendor.getId());
			}
	}
	
	public Vendor findVendorByName(String name){
		Set<Vendor> vendors = vendorRepository.findVendorByName(name);
		if(vendors.size() ==0){
			return null;
		}
		else if(vendors.size() > 1){
			throw new V2GenericException("More than 1 vendors exist");
		}
		else{
			Vendor ven[] = new Vendor[vendors.size()];
			ven = vendors.toArray(ven);
			return ven[0];
		}
	}
	
	public Vendor findVendorByEmail(String email){
		Set<Vendor> vendors = vendorRepository.findVendorByEmail(email);
		if(vendors.size() ==0){
			return null;
		}
		else{
			Vendor ven[] = new Vendor[vendors.size()];
			ven = vendors.toArray(ven);
			return ven[0];
		}
	}
	
	/**
	 * 
	 * @param ven
	 * @return
	 */
	public Vendor saveOrUpdateVendor(Vendor ven){
		validate(ven);
		Vendor vendor = findVendorByName(ven.getName());
		if(vendor == null){
			vendor = ven;
			Set<Vendor> v= vendorRepository.findVendorByEmail(ven.getEmail());
			if(v.size() > 0){
				throw new V2GenericException("Email passed exists. You can not create a vendor with an email that belongs to someone else ");
			}
		}
		else{
			if(!vendor.getEmail().equals(ven.getEmail())){
				/**
				 * Vendor email has changed for existing user.
				 * Figure out whether the changed email exists for some other user. 
				 * If yes, don't allow to change the email
				 */
				Set<Vendor> v= vendorRepository.findVendorByEmail(ven.getEmail());
				if(v.size() > 0){
					throw new V2GenericException("Email passed exists. You can not create a vendor with an email that belongs to someone else ");
				}
			}
			
			Mapper  mapper = new DozerBeanMapper();
			ven.setId(vendor.getId());
			mapper.map(ven, vendor);
			
		}
		return vendorRepository.save(ven);
	}
}

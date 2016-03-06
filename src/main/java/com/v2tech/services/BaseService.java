package com.v2tech.services;

import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Base;

@Service
public class BaseService {
	
	public void validate(Base base){
		if(base.getTenantId() == null || base.getTenantId().trim().length() == 0){
			throw new V2GenericException("Tenant Id can not be null");
		}
	}

}

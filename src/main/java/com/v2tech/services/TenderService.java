package com.v2tech.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.activation.DataHandler;


import org.apache.commons.lang.Validate;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.FileProperty;
import com.v2tech.domain.Project;
import com.v2tech.domain.Tender;
import com.v2tech.repository.FilePropertyRepository;
import com.v2tech.repository.TenderRepository;
import com.v2tech.util.UtilService;

@Service

public class TenderService extends BaseService{
	@Autowired
	TenderRepository tenderRepository;
	
	@Autowired
	FilePropertyRepository filePropertyRepository;
	
	public Tender findTenderByUniqueId(String tenderUniqueId){
		Set<Tender> tenders = tenderRepository.findTenderByUniqueId(tenderUniqueId);
		if(tenders.size() == 0){
			return null;
		}
		else if(tenders.size() > 1){
			throw new V2GenericException("More than 1 tenders exist with same unique id");
		}
		else{
			Tender tends[] = new Tender[tenders.size()];
			tends = tenders.toArray(tends);
			return tends[0];
		}
	}
	
	
	/**
	 * Returns tenderUniqueId
	 * @param tender
	 * @return
	 */
	public String saveOrUpdateTender(Tender tender){
		validate(tender);
		Tender tnd = findTenderByUniqueId(tender.getTenderUniqueId());
			if(tnd == null){
				//create mode;
				tnd = tender;
				tnd.setTenderUniqueId(tender.getProjectName()+System.currentTimeMillis());
				
			}
			else{
				//update mode
				//tender.setFileProperties(tnd.getFileProperties());
				tender.setItems(tnd.getItems());
				tender.setId(tnd.getId());
				Mapper mapper = new DozerBeanMapper();
				mapper.map(tender, tnd);
			}
			tnd = tenderRepository.save(tnd);
			return tnd.getTenderUniqueId();
	}
	
	
	public void addFileToTender(String tenantId, String tenderUniqueId, String fileName, String fileDesc, DataHandler dataHandler){
		String fileUrl = UtilService.createFileAndReturnUrl(dataHandler, fileName, tenderUniqueId);
		FileProperty fileProperty = new FileProperty();
		fileProperty.setFileName(fileName);
		fileProperty.setFileURL(fileUrl);
		fileProperty.setTenantId(tenantId);
		fileProperty.setTenderUniqueId(tenderUniqueId);
		fileProperty.setDescription(fileDesc);
		filePropertyRepository.save(fileProperty);
	}
	
	public void removeFileFromTender(Long filePropertyId){
		filePropertyRepository.delete(filePropertyId);
	}
	
	public void removeTender(Long tenderId){
		tenderRepository.delete(tenderId);
	}
	
	public Set<Tender> getAllTenders(){
		 org.springframework.data.neo4j.conversion.Result<Tender> tenders = tenderRepository.findAll();
		 Iterator<Tender> itr = tenders.iterator();
		 Set<Tender> tenders1 = new HashSet();
		 while(itr.hasNext()){
			 tenders1.add(itr.next());
		 }
		 return tenders1;
	}
	
	
	public void deleteTender(String tenderUniqueId){
		Tender delTender = findTenderByUniqueId(tenderUniqueId);
			if(delTender != null){
				tenderRepository.delete(delTender.getId());
			}
	}

}

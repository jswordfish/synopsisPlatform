package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class FileProperty extends Base{
	
	@GraphId
	private Long id;
	
	String fileName;
	
	String tenderUniqueId;
	
	String fileURL;
	
	String description;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenderUniqueId() {
		return tenderUniqueId;
	}

	public void setTenderUniqueId(String tenderUniqueId) {
		this.tenderUniqueId = tenderUniqueId;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	
	

}

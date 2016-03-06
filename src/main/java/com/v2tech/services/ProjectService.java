package com.v2tech.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Project;
import com.v2tech.domain.Vendor;
import com.v2tech.repository.ProjectRepository;

@Service

public class ProjectService extends BaseService{
	@Autowired
	ProjectRepository projectRepository;
	
	public Project findProjectByName(String name){
		
		Set<Project> projs = projectRepository.findProjectByName(name);
		if(projs.size() == 0){
			return null;
		}
		else if(projs.size() > 1){
			throw new V2GenericException("Ca not be more than 1 projects of same name");
		}
		else{
			Project projects[] = new Project[projs.size()];
			projects = projs.toArray(projects);
			return projects[0];
		}
	}
	
	public Set<Project> getAllProjects(){
		 org.springframework.data.neo4j.conversion.Result<Project> projs = projectRepository.findAll();
		 Iterator<Project> itr = projs.iterator();
		 Set<Project> projects = new HashSet();
		 while(itr.hasNext()){
			 projects.add(itr.next());
		 }
		 return projects;
	}
	
	public void deleteProject(String name){
		Project delProj = findProjectByName(name);
			if(delProj != null){
				projectRepository.delete(delProj.getId());
			}
	}
	
	

	public void saveOrUpdateProject(Project project){
		validate(project);
		Project proj = findProjectByName(project.getProjectName());
			if(proj == null){
				//create mode
				proj = project;
			}
			else{
				Mapper  mapper = new DozerBeanMapper();
				project.setId(proj.getId());
				mapper.map(project, proj);
			}
			proj = projectRepository.save(proj);
		
		
	}
	
	
	public String[] listProjectNames(){
		Set<Project> projects = getAllProjects();
		String projs[] = new String[projects.size()];
		Iterator<Project> itr = projects.iterator();
		int count = 0;
			while(itr.hasNext()){
				Project p = itr.next();
				projs[count] = p.getProjectName();
				count ++;
			}
		return projs;
		
	}

}

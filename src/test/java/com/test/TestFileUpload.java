package com.test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.v2tech.domain.User;

public class TestFileUpload {
	static List<Object> providers = new ArrayList<Object>();
	static WebClient webClient = null;

	@Before
		public void setUp() throws Exception {
			try {
				providers.add(new JacksonJsonProvider() );
				//webClient = WebClient.create("http://utilityapplications-socialapp.rhcloud.com/ws/rest/pdfBoxService/pdfToJavaOutput", providers);
				webClient = WebClient.create("http://localhost/acrossBeyond-1.0/ws/rest/bidderService/uploadFileForTender/tenderUniqueNo/101/tenantId/tenant1", providers);
				webClient.header("Content-Type", "multipart/form-data");
				webClient.type(MediaType.MULTIPART_FORM_DATA_TYPE);
				HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
				conduit.getClient().setReceiveTimeout(0);
				webClient.accept("application/json").type("multipart/form-data");
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}
	
	@Test
	public void testCreateUser() throws JsonProcessingException{
		User user = new User();
		user.setUserEmail("jatinsut@yahoo.com");
		user.setPassword("secret");
		user.setUserType("Admin");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(user));
	}
	
	@Test
	@Rollback(value=false)
	public void testUploadFile(){
		try {
		   ContentDisposition cd = new ContentDisposition("attachment;filename=appConfig.properties");
		   List<Attachment> atts = new LinkedList<Attachment>();
		   FileInputStream fis = new FileInputStream("appConfig.properties");
		   Attachment att = new Attachment("root", fis, cd);
		   atts.add(att);
		   Response res=  webClient.post(att);
		   Assert.assertEquals(true, true);
		} catch(Exception e){
			e.printStackTrace();
			Assert.assertEquals(true, false);
			//logErrorStack(e);
		}	
		
	}
	
}

package com.v2tech.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Tender;

public class EmailThread implements Runnable{

	static String str = null;
	
	static String strPlain;
	
	Tender tender;
	
	String emails[];
	
	String links = "";
	
	String password;
	static{
		
		try {
			str = FileUtils.readFileToString(new File("templates/inviteForTender.template"));
			strPlain = FileUtils.readFileToString(new File("templates/inviteForTenderPlain.template"));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Serious Problem");
			e.printStackTrace();
		}
	}
	
	
	public EmailThread(Tender tender, String[] emails, String htmlLinks){
	this.tender = tender;
	this.emails = emails;
	this.links = htmlLinks;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			StringTemplate template = new StringTemplate(strPlain, DefaultTemplateLexer.class);

			// Create the email message
			  Email email = new SimpleEmail();
			  String host = UtilService.getProperty("hostName");
			  String from = UtilService.getProperty("sendFrom");
			  String fromName = UtilService.getProperty("sendFromName");
			  String pass = UtilService.getProperty("sendFromPwd");
			  String smtpPort = UtilService.getProperty("smtpPort");
			  email.setHostName(host);
			  //email.addTo("nrajpal@acrossbeyond.com");
			  email.addTo("jatin.sutaria@thev2technologies.com");
			  email.setFrom(from, fromName);
			  email.addBcc(emails);
			 
				  template = new StringTemplate(strPlain, DefaultTemplateLexer.class);
				  template.setAttribute("tenderNumber", tender.getTenderUniqueId());
				  template.setAttribute("tenderEndDate", tender.getTenderSubmissionTime());
				  template.setAttribute("projectName", tender.getProjectName());
				  
				  template.setAttribute("email", tender.getEmail());
				  template.setAttribute("scopeOfWork", tender.getScopeOfWork());
				  template.setAttribute("links", this.links);
				  
				  String msg = template.toString();
				  email.setSubject("Inviting vendors to Bid for Tender no - "+tender.getTenderUniqueId());
				  email.setMsg(msg);
		
			 
				  email.setAuthenticator(new DefaultAuthenticator(from, pass)	);
				  email.setTLS(true);
				  //email.setSmtpPort(Integer.parseInt(smtpPort));
				  email.setSSL(true);

				  // send the email
				  email.send();
				  System.out.println("Email Sent");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new V2GenericException("Can not send Email", e);
		}
	}

}
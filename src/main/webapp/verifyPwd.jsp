<%@page import="org.apache.commons.codec.binary.Base64"%> <%@page import="com.v2.booksys.common.util.UserVerficationClient"%>
<%

	String verString = request.getParameter("verificationString");
	String socialMediaType = request.getParameter("socialMediaType");
	
	
	String result = UserVerficationClient.verifyUser( verString, socialMediaType);
	result = result.replace("Msg-", "");
%>

<% if(result.equalsIgnoreCase("Success") ){ %>
<h2><b> You have successfully verified your credentials. Please click below to proceed. </b></h2>

<% } else { %>

<h2><b> Validation failed because of following reason - <%= result.replace("Msg-","") %>. Please click below to proceed. </b></h2>

<% } %>

<p>
<h2> <a href="index.html">Click here and Proceed</a> </h2>

</p>
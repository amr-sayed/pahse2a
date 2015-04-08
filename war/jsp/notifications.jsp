<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    <%@page import="com.FCI.SWE.Controller.UserController"%>
    <%@page import="java.util.ArrayList"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Notification</title>
</head>
<body>
<h1><center>  </center></h1>
  
</body>
</html>



 <%
 
 System.out.println("strat Fetching");
	ArrayList<String>notificaionHyperLink = UserController.notificaionHyperLink ;
	 int i = 1 ;
	for(String url : notificaionHyperLink ){
		System.out.println((i++)+ " - " + url);
		out.println((i++)+ " - " + url);
	}
 %>


   
 <form action="/social/acceptRqust" method="post">
  Name : <input type="text" name="fname" /> <br>
   
  <input type="submit" value="Confirm">
  
  </form>
  
  
       
</body>
</html>
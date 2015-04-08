package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.Services.MessgServices;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.messegEntity;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController extends MessgServices  {
	String link = "http://id20120262.appspot.com/";
	public static ArrayList<String> notificaionHyperLink = new ArrayList<String>() ;
	
	/**
	 
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * Action function to logout by setting the active user to null using url
	 * like this /rest/logout
	 * 
	 * @return entryPoint page
	 */
	@GET
	@Path("/logout")
	public Response logout() {
		User.currentActiveUser = null;
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
           System.out.print("######################################################################################################################################################################################################");
		String serviceUrl =link+ "rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {

		String urlParameters = "uname=" + uname + "&password=" + pass;

		String retJson = Connection.connect(
				link+"rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Action function to response to addFriend request
	 * 
	 */
	@POST
	@Path("/addFriend")
	//@Produces(MediaType.TEXT_PLAIN)
	public String addFriend(@FormParam("email") String email) {
	
		String urlParameters = "email=" + email;
		String retJson = Connection.connect(
				link+"rest/addFriendService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return "User not found";
			else{
				return "Friend Request was send to "
						+ object.get("email").toString();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	@GET
	@Path("/FriendRequsts/")
	public Response frindRequsts() {
		System.out.println("User Controller FrindRequsts");
		return Response.ok(new Viewable("/jsp/FriendRequests")).build();
	}
	
	@POST
	@Path("/acceptRqust")
	@Produces(MediaType.TEXT_PLAIN)
	public Response responseAccept(@FormParam("fname") String fname) {
		System.out.println("User Controller acceptRqust");
		String urlParameters = "fname=" + fname ;
	 	
		String retJson = Connection.connect(
				link+"rest/frindConfirmService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return  Response.ok("failed").build();
			else if (object.get("Status").equals("True"))
				return Response.ok("fried request accepted").build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		 
		return null;
	}
	


	@Path("/responseSeeFriendRequsts")
	@Produces("text/html")
	@POST
	public Response responseSeeFriendRequsts( ) {
		System.out.println("responseSeeFriendRequsts");
		String serviceUrl = "rest/seeFriendRequsts";
		String urlParameters = "";
			//	"uname=" + uname + "&email=" + email+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			 //System.out.println("retJson  "+retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/showFriends")).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;
		//seeFriendRequsts
	}
	
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/sendmessg")
	public Response sendMesseg() {
		return Response.ok(new Viewable("/jsp/sendmessg")).build();
	}
	
	
	@Path("/sendmessgtouser")
	@Produces("text/html")
	@POST
	public String sendMessgToUser(@FormParam("json") String json) {
		
		 //System.out.println("++++++++++"+json.toString());
		JSONObject recvers = new JSONObject();
		recvers.put("Recever1", "4");
		recvers.put("Recever2", "Alaa");
		recvers.put("Recever3", "1");
		recvers.put("Recever4", "2");
		recvers.put("Recever5", "3");
		recvers.put("Recever6", "5");
		JSONObject object = new JSONObject();
		object.put("Messeg", "hello");
	
		object.put("Recevers", recvers);
		String urlParameters = "json="+  json;


		String serviceUrl =link+ "rest/messegsendservice";
		String retJson = Connection.connect(serviceUrl,urlParameters , "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
	
		 
	  
		return null;
		
	}
	
	
	
	
 
	@Produces("text/html")
	@GET
	@Path("/getnotifiacaton")
	 
	public Response getnotifiacaton()   {
		
		
		JSONParser parser = new JSONParser();
		Object obj;
		if (User.getCurrentActiveUser()==null)
			return null;
		
		ArrayList<Pair<String, String>> allNotifications =User.getCurrentActiveUser().getNotification();
		System.out.println("Allnotification"+allNotifications);
		
		for(Pair<String, String> item : allNotifications)
		{
			
			if(item.second.toString().equals("newMesseg")){
				System.out.println(item);
				String conversationId = item.first;
				Pair<String, String> messegTextSender = messegEntity.getMessegDataByGroubId(conversationId);
				String txt= messegTextSender.first;
				String sender= messegTextSender.second;
				//System.out.println(notificaionHyperLink+"$$$$$$$$$$$$$$$$$$$$$$4");
				notificaionHyperLink.add("<a href=\"#/\">new messege groub [ "+conversationId+" ]  Sender [ "+sender+" ]     Text [ "+txt+" ]</a>");
			}
		}
		/*Map<String, String> map = new HashMap<String, String>();
		User user = User.getUser(object.toJSONString());
		map.put("name", user.getName());
		map.put("email", user.getEmail());
		return Response.ok(new Viewable("/jsp/home", map)).build();
		*/
		//System.out.println(notificaionHyperLink+"##@#@#@###@#@");
		return Response.ok(new Viewable("/jsp/notifications") ).build();
	}
	

}
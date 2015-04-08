package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Messege;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;

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
@Produces(MediaType.TEXT_PLAIN)
public class MessgServices   {
	
	
	 

		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param json
	 *            all recevers and messg
		 * @throws ParseException 
	 */
	@POST
	@Path("/messegsendservice")
	public String messegsendservice(@FormParam("json") String json) throws ParseException {
		String messeg ;
		ArrayList<String> users = new ArrayList () ;
		
		JSONParser parser=new JSONParser();
		Object obj=parser.parse(json);
		JSONObject jsonObj = new JSONObject((Map) obj);
	    //System.out.println("json" + jsonObj.toString());
		//System.out.println("Messeg" + jsonObj.get("Messeg"));
		messeg =(String) jsonObj.get("Messeg");
		JSONObject user=(JSONObject)jsonObj.get("Recevers");
	   //System.out.println( user.toString());
		//System.out.println( user.get("Recever2"));
		
		
		ArrayList<String>  x =  new ArrayList(user.keySet());
		//System.out.println("Messeg:"+messeg+"\n"+"Users"+x.toString());
		 
		for( String key : x){
			//System.out.println(key);
			//System.out.println( "XXXXXXXXXXXXXXXXX"+user.get(key));
		    users.add((String) user.get(key));
		}
		Messege opjct =new Messege();
		opjct.sendMesseg(messeg, users);
		//System.out.println("Messeg:"+messeg+"\n"+"Users"+users.toString());
		 
		return null;
	}

}
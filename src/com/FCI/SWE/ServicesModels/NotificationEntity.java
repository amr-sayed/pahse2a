package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Messege;
import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;

 
public abstract class NotificationEntity {
	
	// MessegUserIdConversatonID convesationID userID
	public static void setUserNotificaton( String userMail , String notificationType , String TypeMetaData){
		 
		//Type newMesseg
		Map<String ,Integer  > conversationIdForEmail = new  HashMap<String ,Integer  > ()  ;
        System.out.println("NotificationEntity for "+ userMail);
       
		 DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Query gaeQuery = new Query("UserNotification");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
	        int newNotificationID =  list.size() + 1 ;
			Entity UserNotification = new Entity("UserNotification");

			UserNotification.setProperty("userMail", userMail );
			UserNotification.setProperty("notificationType", notificationType);
			UserNotification.setProperty("metaData", TypeMetaData);
			UserNotification.setProperty("seen", "0");
			datastore.put(UserNotification);
		}
	public static ArrayList<Pair<String, String>> getUserNotificaton( String mail ){
		 
		//System.out.println(" search for notifcation ");
	       
			ArrayList<Pair<String, String>> notification = new  ArrayList<Pair<String, String>> () ;
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query gaeQuery = new Query("UserNotification");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				//System.out.println(" search for notifcation # " +entity .toString());
				if ( entity.getProperty("userMail").toString().equals(mail)  && entity.getProperty("seen").toString().equals("0") ){
					
					Pair<String, String> myPair =new Pair<String, String>(entity.getProperty("metaData").toString(), entity.getProperty("notificationType").toString());
					notification.add(myPair);
					//System.out.println(" get this " + myPair.toString());
				     entity.setProperty("seen", "1");
				     datastore.put(entity);
				}
			}
			return notification;
		}
	
 

	

	}

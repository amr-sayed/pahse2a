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

 
public abstract class messegEntity {
	
	// MessegUserIdConversatonID convesationID userID
	public static ArrayList<String > getGroubId(ArrayList<String> users){
		ArrayList<String > retetnArray= new ArrayList<String >();
		//String  conversationID, int  coubt
		Map<String ,Integer  > conversationIdForEmail = new  HashMap<String ,Integer  > ()  ;
        System.out.println("MessegEntity");
        DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
         // get and count all convesationID for htis Users
		 Query gaeQuery = new Query("MessegUserIdConversatonID");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		int newSize =  0 ;
 		for (Entity entity : pq.asIterable()) {
 			++ newSize ;
			 System.out.println( entity.toString());
			 String mail = entity.getProperty("userID").toString();
			 if(users.contains(mail)){
				 String convesationID =entity.getProperty("convesationID").toString() ;
				 System.out.println( "get this User:"+ mail + "convesationID:"+convesationID);
				 
				 
				 if (conversationIdForEmail.containsKey(convesationID)){
					 conversationIdForEmail.put(convesationID,conversationIdForEmail.get(convesationID)+1);
				 }else {
					 conversationIdForEmail.put(convesationID, 1);
				 }
				 
			 }
			 
		} 
		
 		
 		System.out.println("Tree"+conversationIdForEmail);
		int size = users.size();
		//find match conversation ID else return count +1
        for(String key : conversationIdForEmail.keySet()){
        	if(conversationIdForEmail.get(key)== size){
        		// false this old conversation groub
        		retetnArray.add("old");
        		retetnArray.add(key);
        		
        		return retetnArray ;
        	}
        }
		
        // true this new conversation groub
        retetnArray.add("new");
		retetnArray.add(Integer.toString(newSize));
		
		return retetnArray ;
       
		
      /*
        Query findFemalesQuery = new Query("users");
        findFemalesQuery.addFilter("email", FilterOperator.EQUAL, "Alaa");
        //findFemalesQuery.addSort("age", SortDirection.ASCENDING);
        datastore.prepare(findFemalesQuery).asList(FetchOptions.Builder.withLimit(10));
       */ 
			
		}
	
	 
	public static  void addUsersAndConversationID(String convesationID ,ArrayList<String> users){	
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("MessegUserIdConversatonID");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults()); 
		int size =list.size() ;
		
		for(String mail : users)
		{
			Entity MessegUserIdConversatonID = new Entity("MessegUserIdConversatonID",++size );
			MessegUserIdConversatonID.setProperty("convesationID", convesationID );
			MessegUserIdConversatonID.setProperty("userID", mail);
			datastore.put(MessegUserIdConversatonID); 
			System.out.println(MessegUserIdConversatonID.toString());
		}
		
		
	}
	
	public static  String writeMessege(String txt ,String senderMail){	
	
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Messege");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
        int newMessgID =  list.size() + 1 ;
		Entity Messege = new Entity("Messege", newMessgID);

		Messege.setProperty("text", txt );
		Messege.setProperty("sender", senderMail);
		Messege.setProperty("messegID", newMessgID);
		datastore.put(Messege);
		return Integer.toString(newMessgID );
		
	}
	
	public static  void addMessegToConversationTable(String convesationID ,String messegID){	
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("MessegeIdConvesationID");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults()); 
		Entity MessegeIdConvesationID = new Entity("MessegeIdConvesationID", list.size()+1);

		MessegeIdConvesationID.setProperty("convesationID", convesationID );
		MessegeIdConvesationID.setProperty("messegID", messegID);
		datastore.put(MessegeIdConvesationID); 
		
	}
	
	public static  String getNewMessegID(){
				Map<String ,Integer  > conversationIdForEmail = new  HashMap<String ,Integer  > ()  ;
		       // System.out.println("getNewMessegID");
		        DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				 Query gaeQuery = new Query("Messege");
				PreparedQuery pq = datastore.prepare(gaeQuery);
				int newSize =  0 ;
		 		for (Entity entity : pq.asIterable()) {
		 			++ newSize ;
				} 
				return Integer.toString(newSize);
				
	}
	
	public static  Pair<String, String>  getMessegDataByGroubId( String conversationID){
		Pair<String, String> senderAndText = new Pair<String, String> ("","");
		String messegID = "" ;

		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query gaeQuery = new Query("MessegeIdConvesationID");
		 PreparedQuery pq = datastore.prepare(gaeQuery);
		 int newSize =  0 ;
	 	 for (Entity entity : pq.asIterable()) {
	 			if(entity.getProperty("convesationID").toString().equals(conversationID)){
	 			messegID = entity.getProperty("messegID").toString();
	 			}
		 } 
	 		
	 		
	 	
	 		Query gaeQuery2 = new Query("Messege");
	 		PreparedQuery pq2 = datastore.prepare(gaeQuery2);
	 		System.out.println("star search for id"+messegID);
	  		for (Entity entity2 : pq2.asIterable()) {
	  			 System.out.println(entity2.toString()+"XXXXXX");
	  			 if(entity2.getProperty("messegID").toString().equals(messegID)){
	  				 System.out.println("geti messeg notifi");
	  				return new Pair<String, String> (entity2.getProperty("text").toString(),entity2.getProperty("sender").toString()); 
	  			 }
	 		} 
	  		System.out.println("not find");
		return  senderAndText;
		
}

	
	

	

	}

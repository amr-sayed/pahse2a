package com.FCI.SWE.Models;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.messegEntity;


public class Messege {
 
	ArrayList<User> messegUsers  ;
	String messegTxt ;
	public String conversationID = null;
	/*public Messege(ArrayList<String> recevers,String messegTxt) {
		// TODO Auto-generated constructor stub
		this.messegTxt=messegTxt;
		ArrayList<User> messegUsers = new ArrayList<User> ();
		for( String mail : recevers ){
			
		}
		messegUsers.add(User.getCurrentActiveUser());
	}*/
	 public boolean sendMesseg (String messegTxt , ArrayList<String> recevers){
		 System.out.println("Messeg:"+messegTxt+"\n"+"Users"+recevers.toString());
		 
		 //get old idGroubConversation or get new one
		 ArrayList<String> checking = messegEntity.getGroubId(recevers) ;
		 conversationID =checking.get(1);
		 System.out.println("conversationID = " +conversationID );
		//write messeg and get messeg id
		 String messegID = messegEntity.writeMessege(messegTxt, User.getCurrentActiveUser().getEmail() ); 
		
		 System.out.println("new messegID = " +messegID );
		 //seend messeg to this id => yu can write it in cnversatioon table
		 messegEntity.addMessegToConversationTable(conversationID,messegID);
		 //write to uuser if ther is new new groub of conversation
		 if(checking.get(0).equals("new")){
			 System.out.println("new conversation");
			 messegEntity.addUsersAndConversationID(conversationID, recevers);
		 }
		 //notyfy all users 
		 ArrayList<User> messegUsers = new ArrayList<User> ();
			for( String mail : recevers ){
				User user =UserEntity.getUserOpjectByEmail(mail);
				if(user != null){
				System.out.println("#####"+user.getPass());
				user.meseegNotification(this);
				}
			}
			messegUsers.add(User.getCurrentActiveUser()); 
		 
		 return false; 
	 }

}

package com.FCI.SWE.Models;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Messege;
import com.FCI.SWE.ServicesModels.NotificationEntity;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;

public class User {
	private long id;
	private String name;
	private String email;
	private String password;
	
	public static User currentActiveUser;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}
	
	private void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}
	
	public static User getCurrentActiveUser(){
		return currentActiveUser;
	}
	
	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static User getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			currentActiveUser = new User(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString());
			currentActiveUser.setId(Long.parseLong(object.get("id").toString()));
			return currentActiveUser;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	 
	public ArrayList<Pair<String, String>>  getNotification()
	{
		ArrayList<Pair<String, String>> allNotification = NotificationEntity.getUserNotificaton(this.email);
		//for(Pair<String, String> notificationItem : allNotification ){ 	}
		return allNotification;
		
	}
	public void meseegNotification(Messege m) {
		// TODO Auto-generated method stub
		System.out.println(m.conversationID.toString() +"   for my mail  " + this.email);
         NotificationEntity.setUserNotificaton(this.email, "newMesseg", m.conversationID);
	}
}

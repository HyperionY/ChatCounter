package edu.handong.scee.hava.hw4.chatcounter;

import java.util.HashMap;

/**
 * 
 * @author HyperionY
 * @Email yjyj989812 21700467@handong.edu
 * 
 *  create : 2018.06.17
 *
 */

public class MessageChecker {
	HashMap<String, Integer> chatOverlap = new HashMap<String, Integer>();// nickname, redundancy
	HashMap<String[], String> userdata = new HashMap<String[], String>();// nickname, date and time, message
	String[] identifier = new String[3];
	int initiator = 0;
	public void mapUser(String nickname, String datetime, String message) {
		identifier[0] = nickname;
		identifier[1] = datetime;
		userdata.put(identifier, message);
	}
	
	
	public HashMap<String, Integer> getMap(){
		for(String[] whoAwhen:userdata.keySet()) {
			String nickname = whoAwhen[0];
			chatOverlap.merge(nickname,1,Integer::sum);
		}
		return chatOverlap;
	}
	
}

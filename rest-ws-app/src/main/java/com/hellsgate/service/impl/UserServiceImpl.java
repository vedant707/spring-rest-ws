package com.hellsgate.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellsgate.request.model.UserDetailsRequest;
import com.hellsgate.response.model.UserRest;
import com.hellsgate.service.UserService;
import com.hellsgate.shared.Utils;


/* DEPENDENCY INJECTION - 
 * 1. Autowire a service layer
 * 2. Constructor based DI */

@Service
public class UserServiceImpl implements  UserService {
	
	Map<String,UserRest> mapstore = new HashMap<String,UserRest>();
	
	@Autowired
	Utils utils;
	
	public UserServiceImpl() {
	}

	public UserServiceImpl(Utils utils) {
		this.utils = utils;
	}

	@Override
	public UserRest createUser(UserDetailsRequest userdetails) {
		System.out.println("Inside service implementation");
		UserRest returnvalue = new UserRest();
		
		//returnvalue.setUserid(userdetails.getUserid());
		returnvalue.setFirstname(userdetails.getFirstname());
		returnvalue.setLastname(userdetails.getLastname());
		returnvalue.setEmail(userdetails.getEmail());
		
		//System.out.println("Response Body : "+returnvalue.toString());
		
		String userid = utils.generateUserId();
		returnvalue.setUserid(userid);
		
		if(!mapstore.containsKey(userid)) {
			mapstore.put(userid, returnvalue);
		}
		
		return returnvalue;
		
	}
	
	public Set<UserRest> getUsers(){
		
		Set<Map.Entry<String, UserRest>> userset = mapstore.entrySet();
		Set<UserRest> usersetvalues = new HashSet<UserRest>();
		
		if(mapstore.isEmpty()) {
			return null;
		}else {
			for(Map.Entry<String, UserRest> temp : userset) {
				usersetvalues.add(temp.getValue());
			}
			return usersetvalues;
		}
	}
	
	@Override
	public UserRest getUser(String userid) {
		
		if(mapstore.containsKey(userid)) {
			return mapstore.get(userid);
		}else {
			return null;
		}
	}

}

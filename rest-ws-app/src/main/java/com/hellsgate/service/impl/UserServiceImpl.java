package com.hellsgate.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
		
		if(mapstore.isEmpty()) {
			mapstore.put(userid, returnvalue);
		}
		
		return returnvalue;
		
	}

}

package com.hellsgate.service;

import java.util.Set;

import com.hellsgate.request.model.UserDetailsRequest;
import com.hellsgate.response.model.UserRest;

public interface UserService {
	
	UserRest createUser(UserDetailsRequest userdetails);
	Set<UserRest> getUsers();
	UserRest getUser(String userid);

}

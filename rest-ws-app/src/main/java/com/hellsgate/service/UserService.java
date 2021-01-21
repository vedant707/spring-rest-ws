package com.hellsgate.service;

import com.hellsgate.request.model.UserDetailsRequest;
import com.hellsgate.response.model.UserRest;

public interface UserService {
	
	UserRest createUser(UserDetailsRequest userdetails);

}

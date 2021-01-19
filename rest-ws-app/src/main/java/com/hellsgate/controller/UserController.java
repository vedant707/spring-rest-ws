package com.hellsgate.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellsgate.request.model.UpdateUserDetailsRequest;
import com.hellsgate.request.model.UserDetailsRequest;
import com.hellsgate.response.model.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

	Map<String,UserRest> mapstore = new HashMap<String,UserRest>();
	
	
	/* HANDLE HTTP GET REQUESTS - Passing parameters in URL & to keep parameters optional/default */
	@GetMapping
	public String getUsers(@RequestParam(value = "page",defaultValue = "2",required = false) int page,
			@RequestParam(value = "limit",defaultValue = "100",required = false) int limit,
			@RequestParam(value = "sort",defaultValue = "desc",required = false) String sort) {
		return "getUsers() method called with page - "+page+" & limit - "+limit+" & sort - "+sort;
	}
	
	/* RETURNING A RESPONSE -  
	 * 1. Returning Java Object as Response
	 * 2. Returning Java Object as XML / JSON [produces,mediatype]
	 * 3. Set Response Status Code [ResponseEntity<T> object] */
	@GetMapping(path="/{userid}",produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> getUser(@PathVariable String userid) {
		//return "getUser() method called. UserId - "+userid;
		
		if(mapstore.containsKey(userid)) {
			return new ResponseEntity<>(mapstore.get(userid),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		/*UserRest returnvalue = new UserRest();
		returnvalue.setFirstname("Itachi");
		returnvalue.setLastname("Uchiha");
		returnvalue.setEmail("clankiller@gmail.com");
		
		return new ResponseEntity<UserRest>(returnvalue,HttpStatus.OK);*/
	}
	
	/* HANDLE HTTP POST REQUEST - 
	 * 1. Reading HTTP POST Request using RequestBody annotation
	 * 2. Validating HTTP POST request body */
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequest userdetails) {
		//return "createUser() method called";
		
		UserRest returnvalue = new UserRest();
		
		//returnvalue.setUserid(userdetails.getUserid());
		returnvalue.setFirstname(userdetails.getFirstname());
		returnvalue.setLastname(userdetails.getLastname());
		returnvalue.setEmail(userdetails.getEmail());
		
		//System.out.println("Response Body : "+returnvalue.toString());
		
		String userid = UUID.randomUUID().toString();
		returnvalue.setUserid(userid);
		
		if(mapstore.isEmpty()) {
			mapstore.put(userid, returnvalue);
		}
		
		return new ResponseEntity<UserRest>(returnvalue,HttpStatus.OK);
	}
	
	
	/* HANDLE HTTP PUT REQUEST */
	@PutMapping(path = "/{userid}", consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public UserRest updateUser(@PathVariable String userid,@Valid @RequestBody UpdateUserDetailsRequest updateuserdetails) {
		//return "updateUser() method called";
		
		UserRest updateuser = mapstore.get(userid);
		
		if(updateuser != null) {
			updateuser.setFirstname(updateuserdetails.getFirstname());
			updateuser.setLastname(updateuserdetails.getLastname());
		}
		
		mapstore.put(userid, updateuser);
		
		return updateuser;
	}
	
	/* HANDLE HTTP DELETE REQUEST */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		mapstore.remove(id);
		return ResponseEntity.noContent().build();
	}
	
}

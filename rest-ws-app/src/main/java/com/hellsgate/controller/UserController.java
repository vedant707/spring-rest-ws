package com.hellsgate.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.hellsgate.exceptionhandler.CustomException;
import com.hellsgate.request.model.UpdateUserDetailsRequest;
import com.hellsgate.request.model.UserDetailsRequest;
import com.hellsgate.response.model.UserRest;
import com.hellsgate.service.UserService;
import com.hellsgate.service.impl.UserServiceImpl;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

	Map<String,UserRest> mapstore = new HashMap<String,UserRest>();
	
	@Autowired
	UserService userservice;
	
	
	/* HANDLE HTTP GET REQUESTS - Passing parameters in URL & to keep parameters optional/default */
	@GetMapping
	public ResponseEntity<Set<UserRest>> getUsers(@RequestParam(value = "page",defaultValue = "2",required = false) int page,
			@RequestParam(value = "limit",defaultValue = "100",required = false) int limit,
			@RequestParam(value = "sort",defaultValue = "desc",required = false) String sort) {
		//return "getUsers() method called with page - "+page+" & limit - "+limit+" & sort - "+sort;
		
		Set<UserRest> usersetresp = userservice.getUsers();
		/*Set<Map.Entry<String, UserRest>> userset = mapstore.entrySet();
		Set<UserRest> userrestset = new HashSet<UserRest>();
		if(mapstore.isEmpty()) {
			return new ResponseEntity<Set<UserRest>>(HttpStatus.NO_CONTENT);
		}else {
			for(Map.Entry<String, UserRest> temp : userset) {
				userrestset.add(temp.getValue());
			}
			return new ResponseEntity<Set<UserRest>>(userrestset,HttpStatus.OK);
		}*/
		if(usersetresp.isEmpty()) {
			return new ResponseEntity<Set<UserRest>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Set<UserRest>>(usersetresp,HttpStatus.OK);
		}
		
	}
	
	
	
	/* RETURNING A RESPONSE -  
	 * 1. Returning Java Object as Response
	 * 2. Returning Java Object as XML / JSON [produces,mediatype]
	 * 3. Set Response Status Code [ResponseEntity<T> object] */
	@GetMapping(path="/{userid}",produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> getUser(@PathVariable String userid) {
		//return "getUser() method called. UserId - "+userid;
		
		/*String firstname = null;
		int firstnamelength = firstname.length();*/
		
		/*if(true)
			throw new CustomException(" In Custom Exception");*/
		
		/*if(mapstore.containsKey(userid)) {
			return new ResponseEntity<>(mapstore.get(userid),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}*/
		
		/*UserRest returnvalue = new UserRest();
		returnvalue.setFirstname("Itachi");
		returnvalue.setLastname("Uchiha");
		returnvalue.setEmail("clankiller@gmail.com"); */
		
		UserRest userresp = userservice.getUser(userid);
		if(userresp!=null) {
			return new ResponseEntity<UserRest>(userresp,HttpStatus.OK);
		}else {
			return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	
	/* HANDLE HTTP POST REQUEST - 
	 * 1. Reading HTTP POST Request using RequestBody annotation
	 * 2. Validating HTTP POST request body */
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequest userdetails) {
		//return "createUser() method called";
		
		// using new creates direct dependency on Service class
		//UserRest userrest = new UserServiceImpl().createUser(userdetails);
		
		// to avoid direct dependency as above, we use DI [Autowired annotation]
		UserRest userrest = userservice.createUser(userdetails);
		
		return new ResponseEntity<UserRest>(userrest,HttpStatus.OK);
		
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

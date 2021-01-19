package com.hellsgate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellsgate.request.model.UserDetailsRequest;
import com.hellsgate.response.model.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

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
	 * 3. Set Response Status Code [ResponseEntity<T> object]*/
	@GetMapping(path="/{userid}",produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> getUser(@PathVariable String userid) {
		//return "getUser() method called. UserId - "+userid;
		
		UserRest returnvalue = new UserRest();
		returnvalue.setFirstname("Itachi");
		returnvalue.setLastname("Uchiha");
		returnvalue.setEmail("clankiller@gmail.com");
		
		return new ResponseEntity<UserRest>(returnvalue,HttpStatus.OK);
	}
	
	/* HANDLE HTTP POST REQUEST - 
	 * 1. Reading HTTP POST Request using RequestBody annotation
	 * 2. Validating HTTP POST request body */
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},produces = 
		{MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequest userdetails) {
		//return "createUser() method called";
		
		UserRest returnvalue = new UserRest();
		
		returnvalue.setUserid(userdetails.getUserid());
		returnvalue.setFirstname(userdetails.getFirstname());
		returnvalue.setLastname(userdetails.getLastname());
		returnvalue.setEmail(userdetails.getEmail());
		
		//System.out.println("Response Body : "+returnvalue.toString());
		
		return new ResponseEntity<UserRest>(returnvalue,HttpStatus.OK);
	}
	
	@PutMapping
	public String updateUser() {
		return "updateUser() method called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "deleteUser() method called";
	}
}

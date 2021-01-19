package com.hellsgate.request.model;

import javax.validation.constraints.NotNull;

public class UpdateUserDetailsRequest {

	@NotNull(message = "Firstname cannot be null")
	private String firstname;
	
	@NotNull(message = "Lastname cannot be null")
	private String lastname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	
}

package com.SpringBootProject.StudentDetails.dto;

public class AuthenticationResponse {
	
	public String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}
	

}

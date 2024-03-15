package com.SpringBootProject.StudentDetails.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.SpringBootProject.StudentDetails.Model.StudentModel;

public class MyUserDetails implements UserDetails {

	@Autowired
	private StudentModel studentmodel;
	
	public MyUserDetails(StudentModel student) {
	    super();
	    this.studentmodel=student;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	SimpleGrantedAuthority authority =new SimpleGrantedAuthority(studentmodel.getRole());
	System.out.println(studentmodel.getRole());
	List<SimpleGrantedAuthority> authorities= new ArrayList<SimpleGrantedAuthority>();
	authorities.add(authority);
	System.out.println(authorities);
	return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return studentmodel.getPassword() ;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return studentmodel.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

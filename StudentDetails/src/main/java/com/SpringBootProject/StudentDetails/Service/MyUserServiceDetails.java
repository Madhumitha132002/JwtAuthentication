package com.SpringBootProject.StudentDetails.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringBootProject.StudentDetails.Model.StudentModel;
import com.SpringBootProject.StudentDetails.Repository.StudentDAO;
import com.SpringBootProject.StudentDetails.model.MyUserDetails;

   
@Service
public class MyUserServiceDetails implements UserDetailsService {
	 @Autowired
	    private StudentDAO studentdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("inside the load user by username");
		StudentModel student=studentdao.findByUsername(username);
		return new MyUserDetails(student) ;
		
	}

}

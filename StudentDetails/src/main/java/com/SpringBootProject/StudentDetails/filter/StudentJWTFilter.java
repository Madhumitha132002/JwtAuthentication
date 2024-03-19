package com.SpringBootProject.StudentDetails.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SpringBootProject.StudentDetails.Model.CustomerModel;
import com.SpringBootProject.StudentDetails.util.StudentJWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class StudentJWTFilter  extends OncePerRequestFilter{
	
	@Autowired
	private StudentJWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userdetailsservice;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt=null;
		String extractUserName=null;
		String header=request.getHeader("Authorization");
		if (header!=null&&header.startsWith("Bearer ")) {
			jwt=header.substring(7); 
			extractUserName = jwtUtil.extractUsername(jwt);
		}
		if (extractUserName!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userdetailsservice.loadUserByUsername(extractUserName);
			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken=new 
						UsernamePasswordAuthenticationToken(null, userDetails,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);	
	}

}

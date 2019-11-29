package com.tcs.bootDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bootDemo.bean.JwtRequest;
import com.tcs.bootDemo.bean.JwtResponse;
import com.tcs.bootDemo.service.MyUserDetailsService;
import com.tcs.bootDemo.util.JwtUtil;

@RestController
public class TestController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
	return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
		final UserDetails userDetails = myUserDetailsService
				.loadUserByUsername(authenticationRequest.getUserName());
		 String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String userName, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		
	}
}

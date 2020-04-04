package com.dj.security.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dj.security.model.AuthenticationRequest;
import com.dj.security.model.AuthenticationResponse;
import com.dj.security.services.MyUserDetailsService;
import com.dj.security.utils.JwtUtil;

@RestController
public class AppMain {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
    
    @GetMapping("/hello")
    public String hello() {
        return ("<h1>Hello World</h1>");
    }
    
    @RequestMapping(value = "/authenticate" , method= RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    	try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
					);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Username or Password");
		}
    	
    	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    	
    	final String jwt = jwtTokenUtil.generateToken(userDetails);
    	
    	return ResponseEntity.ok(new AuthenticationResponse(jwt));
    	
    	
    }
}

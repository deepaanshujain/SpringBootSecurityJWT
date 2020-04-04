package com.dj.security.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dj.security.entity.MyUserDetails;
import com.dj.security.repo.UserRepository;
import com.dj.security.entity.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	 @Autowired
	 UserRepository userRepository;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUser(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return user.map(MyUserDetails::new).get();
	}
	
	public  Map<String, Object>  getUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);

        Map<String, Object> userMap = new HashMap<>();
        
        userMap.put("userId", user.getId());
        userMap.put("userName", user.getUserName());
        return userMap;
	}
	
	

}

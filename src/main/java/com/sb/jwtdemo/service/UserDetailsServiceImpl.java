package com.sb.jwtdemo.service;

import static java.util.Collections.emptyList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.jwtdemo.entity.User;
import com.sb.jwtdemo.entity.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User user = this.userRepository.findByUsernameCaseInsensitive(username);

		if (user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		
		try {
			LOGGER.info("User found: {}", new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

        return new ApplicationUserDetails(
        		user.getId(),
                user.getUsername(),
                user.getPassword(),
                emptyList());
	}
	
	public void signup (User user) throws Exception {
		User repoUser = this.userRepository.findByUsernameCaseInsensitive(user.getUsername());

		if (repoUser != null) {
			LOGGER.info("User {} already existed {}", repoUser.getUsername());
			throw new Exception ("User already existed") ;
		}
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}
}

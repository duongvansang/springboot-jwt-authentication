package com.sb.jwtdemo.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.jwtdemo.entity.User;
import com.sb.jwtdemo.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) throws Exception {
		userService.signup(user);
		
		APITemplate res = new APITemplate(HttpStatus.OK, "User created");
		return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

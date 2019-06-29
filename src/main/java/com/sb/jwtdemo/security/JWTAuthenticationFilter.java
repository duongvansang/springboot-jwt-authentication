package com.sb.jwtdemo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.jwtdemo.api.APITemplate;
import com.sb.jwtdemo.service.ApplicationUserDetails;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private boolean postOnly = true;
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
    
    	 if (this.postOnly && !req.getMethod().equals("POST")) {
             throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
         } 
    	 
    	 try {
             AuthenticationRequest authen = new ObjectMapper()
                     .readValue(req.getInputStream(), AuthenticationRequest.class);
             String username = authen.getUsername();
             String password = authen.getPassword();
             if (username == null) {
                 username = "";
             }
             if (password == null) {
                 password = "";
             }

             return authenticationManager.authenticate(
             		new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
             );
         } catch (IOException e) {
         	throw new AuthenticationServiceException("Invalid login request");
         }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	
    	APITemplate ret = new APITemplate(HttpStatus.OK, "Operation succeeded");
    	Map<String, Object> data = new HashMap<>();
    	
        String token = JWT.create()
                .withSubject(((ApplicationUserDetails) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JWTConfig.SECRET.getBytes()));
        
       
        data.put("token", token);
    	data.put("expired_in", JWTConfig.EXPIRATION_TIME/1000);
    	data.put("token_type", "bearer");
    	
    	ret.setData(data);
        res.getWriter().println(MAPPER.writeValueAsString(ret));
    }
    
    /**
     */
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        APITemplate ret = new APITemplate(HttpStatus.UNAUTHORIZED.name(), failed.getMessage(), HttpStatus.UNAUTHORIZED);
        response.getWriter().println(new ObjectMapper().writeValueAsString(ret));
        SecurityContextHolder.clearContext();
    }
}

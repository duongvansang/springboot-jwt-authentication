package com.sb.jwtdemo.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sb.jwtdemo.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	.anyRequest().authenticated()
        	.and()
            .cors()
            .and()
            .requestCache().disable()
            .csrf().disable().authorizeRequests()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), BasicAuthenticationFilter.class)
            .sessionManagement().disable();
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring()
              .antMatchers("/v1/user/signup/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  
    	String names[] = new String[]{"GET","POST","DELETE","PUT", "HEAD"};
    	List<String> namesList = Arrays.asList(names);
    	CorsConfiguration config = new CorsConfiguration();
    	config.setAllowedMethods(namesList);
    	config.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("*")));
    	config.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList("*")));
  
    	source.registerCorsConfiguration("/**", config);
    	return source;
    }
}

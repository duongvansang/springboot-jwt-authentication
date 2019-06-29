package com.sb.jwtdemo.security;

public class JWTConfig {
	public static final String SECRET = "jwtdemokey";
    public static final long EXPIRATION_TIME = 14400000; // 4 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}

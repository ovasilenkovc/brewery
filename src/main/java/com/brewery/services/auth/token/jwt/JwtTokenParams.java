package com.brewery.services.auth.token.jwt;

public class JwtTokenParams {

    static final long EXPIRATION_TIME = 90;
    static final String SECRET = "brewery";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}

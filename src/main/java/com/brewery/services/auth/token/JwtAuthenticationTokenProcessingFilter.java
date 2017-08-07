package com.brewery.services.auth.token;

import com.brewery.exceptions.JwtAuthentiacationException;
import com.brewery.exceptions.JwtTokenMissingException;
import com.brewery.services.auth.token.jwt.JwtToken;
import com.brewery.services.auth.token.jwt.JwtTokenParams;
import com.brewery.services.auth.user.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private CustomUserDetailService userDetailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthentiacationException.class);

    public JwtAuthenticationTokenProcessingFilter() {
        super("/**");
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/**"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authToken = request.getHeader(JwtTokenParams.HEADER_STRING);
        LOGGER.info("User authentication process started");

        if ((authToken == null || authToken.isEmpty()) || !authToken.startsWith(JwtTokenParams.TOKEN_PREFIX)) {
            LOGGER.error("Token was not found");
            throw new JwtTokenMissingException("Token was not found");
        }

        if (!userDetailService.isValidToken(authToken.substring(JwtTokenParams.TOKEN_PREFIX.length()))) {
            LOGGER.error("This token has been expired!");
            throw new JwtAuthentiacationException("This token has been expired!");
        }

        JwtToken token = new JwtToken(authToken.substring(JwtTokenParams.TOKEN_PREFIX.length()));
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        LOGGER.info("User has been authenticated successfully!");
        chain.doFilter(request, response);
    }


}


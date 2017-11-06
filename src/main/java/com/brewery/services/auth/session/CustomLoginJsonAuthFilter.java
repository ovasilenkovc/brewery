package com.brewery.services.auth.session;

import com.brewery.admin.auth.User;
import com.brewery.utils.ConstantParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * Class filter for custom authentication by JSON RESTful service
 */
public class CustomLoginJsonAuthFilter extends UsernamePasswordAuthenticationFilter {

    private String jsonUsername;
    private String jsonUserPass;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password;

        if ("application/json".equals(request.getHeader("Content-Type"))) {
            password = this.jsonUserPass;
        } else {
            password = request.getParameter("password");
        }

        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username;

        if ("application/json".equals(request.getHeader("Content-Type"))) {
            username = this.jsonUsername;
        } else {
            username = request.getParameter("username");
        }

        return username;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        User userModel;

        if (ConstantParams.JSON_HEADER_TYPE.equals(request.getHeader("Content-Type")) && request.getMethod().equals("POST")) {
            userModel = getUserFromJsonREquest(request);
        } else {
            userModel = getUserFromFormDataRequest(request);
        }

        this.jsonUsername = userModel.getUsername();
        this.jsonUserPass = userModel.getPassword();
        return super.attemptAuthentication(request, response);
    }

    private User getUserFromFormDataRequest(HttpServletRequest request) {

        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));

        return user;
    }

    private User getUserFromJsonREquest(HttpServletRequest request) {
        User userModel = new User();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String line = null;
            StringBuilder sb = new StringBuilder();

            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            if (sb.length() > 0) {
                userModel = mapper.readValue(sb.toString(), User.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userModel;
    }

}

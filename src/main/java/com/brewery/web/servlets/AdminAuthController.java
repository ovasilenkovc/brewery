package com.brewery.web.servlets;

import com.brewery.admin.auth.User;
import com.brewery.services.auth.token.jwt.JwtLoginTokenRequest;
import com.brewery.services.auth.token.jwt.JwtTokenParams;
import com.brewery.services.auth.token.jwt.JwtTokenService;
import com.brewery.services.auth.user.CustomUserDetailService;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ResponseMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller which realize Admin user authentication.
 */
@Controller
public class AdminAuthController {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private CustomUserDetailService userDetailService;

    private static final Logger LOGGER = Logger.getLogger(AdminAuthController.class);

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> jsonLogin(@RequestBody @Valid JwtLoginTokenRequest credentials) throws Exception {
        LOGGER.info("User login method called");
        Map<String, String> response = new HashMap<>();
        User userDetails = userDetailService.getUserDetailsByUserName(credentials.getUsername());

        if (credentials.getPassword().equals(userDetails.getPassword())) {
            String token = tokenService.buildToken(userDetails);
            response.put("token", token);
        } else {
            LOGGER.error("Bad credentials");
            return ResponseMaker.makeResponse("Bad credentials", ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("User has been logged successfully!");
        return ResponseMaker.makeResponse(response, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/authorisation", method = RequestMethod.GET)
    public ModelAndView authorisation() throws Exception {
        LOGGER.info("auth page called");
        return new ModelAndView("login");
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<String> jsonLogout(HttpServletRequest request) throws Exception {
        String header = request.getHeader(JwtTokenParams.HEADER_STRING);
        LOGGER.info("User logout method called");
        try {
            if (!userDetailService.isValidToken( header.substring(JwtTokenParams.TOKEN_PREFIX.length()))) {
                LOGGER.info("token has already been invalidated!");
                return ResponseMaker.makeResponse("token has already been invalidated!",
                        ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
            }
            String token = header.substring(JwtTokenParams.TOKEN_PREFIX.length());
            User user = tokenService.parseToken(token);
            if(user != null){
                userDetailService.invalidateToken(token);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new Exception(e.getMessage());
        }

        LOGGER.info("User has been logged out successfully!");
        return ResponseMaker.makeResponse("token has been invalidated successfully",
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

}

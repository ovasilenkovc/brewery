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
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller which realize Admin user authentication.
 */
@Controller
public class AdminAuthController {

    private String token = "";
    private boolean isSessionAlive = false;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private CustomUserDetailService userDetailService;

    private static final Logger LOGGER = Logger.getLogger(AdminAuthController.class);

    @RequestMapping("/")
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse) throws Exception {

        ModelAndView modelAndView = new ModelAndView("index");
        String newLocale = httpServletRequest.getParameter("language");
        if (newLocale != null) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(httpServletRequest);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            localeResolver.setLocale(httpServletRequest, httpServletResponse, StringUtils.parseLocaleString(newLocale));
        }

        modelAndView.addObject("token", token);
        modelAndView.addObject("authenticated", isSessionAlive);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView jsonLogin(@ModelAttribute("userForm") JwtLoginTokenRequest credentials,  RedirectAttributes redirectAttrs) throws Exception {
        LOGGER.info("User login method called");
        User userDetails = userDetailService.getUserDetailsByUserName(credentials.getUsername());

        if (credentials.getPassword().equals(userDetails.getPassword())) {
            token = tokenService.buildToken(userDetails);
        } else {
            LOGGER.error("Bad credentials");
            return new ModelAndView("login");
        }

        LOGGER.info("User has been logged successfully!");
        isSessionAlive = true;
        return new ModelAndView("redirect:/");
    }


    @ResponseBody
    @RequestMapping(value = "/authorisation", method = RequestMethod.GET)
    public ModelAndView authorisation() throws Exception {
        LOGGER.info("auth page called");
        ModelAndView model = new ModelAndView("login");
        JwtLoginTokenRequest user = new JwtLoginTokenRequest();
        model.addObject("loginUser", user);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<String> jsonLogout(HttpServletRequest request) throws Exception {
        String header = request.getHeader(JwtTokenParams.HEADER_STRING);
        LOGGER.info("User logout method called");
        try {
            if (!userDetailService.isValidToken( header.substring(JwtTokenParams.TOKEN_PREFIX.length()))) {
                LOGGER.info("token has already been invalidated!");
                return ResponseMaker.makeResponse("token has already been invalidated!", ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
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
        isSessionAlive = false;
        token = "";
        return ResponseMaker.makeResponse("token has been invalidated successfully", ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

}

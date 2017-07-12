package com.brewery.services.auth.token.job;

import com.brewery.admin.auth.InvalidToken;
import com.brewery.admin.dao.AdminUserDAO;
import com.brewery.services.auth.token.jwt.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClearInvalidatedToken {

    @Autowired
    private AdminUserDAO adminUserDao;

    @Autowired
    private JwtTokenService tokenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearInvalidatedToken.class);

    public ClearInvalidatedToken() {
    }

    @Scheduled(fixedDelay=7200000)
    public void clearInvalidatedToken(){
        LOGGER.info("Invalidate tokens Database cleaning started");
        List<InvalidToken> invalidTokens = adminUserDao.getInvalidatedTokens();
        for (InvalidToken invalidToken: invalidTokens){
            String token = invalidToken.getToken();
            if(tokenService.isTokenExpired(token)){
                adminUserDao.removeInvalidatedToken(invalidToken);
            }
        }
    }
}

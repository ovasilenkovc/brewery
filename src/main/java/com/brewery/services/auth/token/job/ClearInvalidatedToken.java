package com.brewery.services.auth.token.job;

import com.brewery.admin.auth.InvalidToken;
import com.brewery.admin.dao.AdminUserDAO;
import com.brewery.services.auth.token.jwt.JwtTokenService;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(ClearInvalidatedToken.class);

    public ClearInvalidatedToken() {
    }

    @Scheduled(fixedDelay = 7200000)
    public void clearInvalidatedToken() {
        LOGGER.info("Invalidate tokens Database cleaning started");
        List<InvalidToken> invalidTokens = adminUserDao.getInvalidatedTokens();
        for (InvalidToken invalidToken : invalidTokens) {
            String token = invalidToken.getToken();
            adminUserDao.removeInvalidatedToken(invalidToken);
        }
    }
}

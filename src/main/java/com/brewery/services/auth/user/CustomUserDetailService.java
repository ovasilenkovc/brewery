package com.brewery.services.auth.user;


import com.brewery.admin.auth.User;

import java.sql.SQLException;

public interface CustomUserDetailService {

    public User getUserDetailsByUserName(String username) throws SQLException;

    public void invalidateToken(String token) throws SQLException;

    public boolean isValidToken(String token);

}

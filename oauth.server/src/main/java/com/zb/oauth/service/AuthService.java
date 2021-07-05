package com.zb.oauth.service;

import com.zb.oauth.util.AuthToken;

public interface AuthService {
    public AuthToken login(String username ,String passwd , String clientId , String clientSecret);
}

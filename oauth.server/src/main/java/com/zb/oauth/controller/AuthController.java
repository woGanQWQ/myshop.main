package com.zb.oauth.controller;

import com.zb.dto.Result;
import com.zb.oauth.service.AuthService;
import com.zb.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;

    @PostMapping("/login")
    public Result login(String username, String passwd) throws Exception {
        AuthToken authToken = authService.login(username, passwd, clientId, clientSecret);
        return Result.succ("登录成功！", authToken);
    }
}

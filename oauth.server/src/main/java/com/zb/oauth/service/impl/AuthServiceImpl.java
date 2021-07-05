package com.zb.oauth.service.impl;

import com.zb.oauth.service.AuthService;
import com.zb.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public AuthToken login(String username, String passwd, String clientId, String clientSecret) {
        List<ServiceInstance> instances = discoveryClient.getInstances("oauth-server");
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/oauth/token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", passwd);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", httpbasic(clientId, clientSecret));
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), Map.class);
        Map data = exchange.getBody();
        //{access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYyMzg5NDQ0OSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiIzNTk0MjBlMy0zZDhkLTRhOGMtYjdlMS05MzlkMWE0NDI4MDgiLCJjbGllbnRfaWQiOiJqcyIsInVzZXJuYW1lIjoiYWNjcCJ9.JEER9x2BfVa_uIQ7rZF66PQ6LJ3xfHNzSJG2AC-ayzdpe6tLC_lVK-tBfHmgLNSIESGppmnwYZwVM-Hnd-DeHxtgpjnExZLHm6rfW2zHihs4HfS0Xi7iVFIzf5azTyUHn8T4ZTgTO4AKReY6YDBHT4tpqMeIfmNr9ugDWyPyjUYeuFtaHjN78FFYadznt3XXKtSo1lTW8ZfmGcDUKfB8hnEi2Fk0rMN75Q0TF_vu4tbVD-g-DiC9JG48M_4Fmvcdz2w3RbYeyVtRr_IsvyzHSGY9HLK-v8LvyYamX7HPgFj8qwcc-EvkhlA9O_HksbznultSMtHS54QH6QhbzXQXdg, token_type=bearer, refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiYXRpIjoiMzU5NDIwZTMtM2Q4ZC00YThjLWI3ZTEtOTM5ZDFhNDQyODA4IiwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYyMzg5NDQ0OSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiJjZjY1OWMwNy1hZjkzLTQ3OTAtYmM3MS1jOGQxMjEyMDczZGMiLCJjbGllbnRfaWQiOiJqcyIsInVzZXJuYW1lIjoiYWNjcCJ9.bdyyUDYenYyg-W3jRmVz5k9bx7ru2S5iXLb5yQCkTZSf8ejEePKfn1lZh1vLlmag3yiuDaLNZN4IwBJygR7lMvx_SU5ndnBwWxTSRT90TiGF_eCGHENQMHUMNqpppKlExDV9wPE0Equz1IXUM9HNVDU5PiWxYqN0f5XXYc69QZQMORleUrbDALWwn4h1L1NdDJ24l9K_dblrakcszeiRxkdfcJb2OOYJ52BPNvaRaaTLcDWYs5pandY4ZQ5DKsfeBVn-OxPCd-tnachmFxf_7DXkSRIC51yXw8x_w0Rv-ZaRKiYG1_voQrrX8KK9-eeBu8pyQVm1SmiN-6ni_ACutw, expires_in=3599, scope=app, jti=359420e3-3d8d-4a8c-b7e1-939d1a442808}
        AuthToken authToken = new AuthToken();
        authToken.setRefreshToken(data.get("refresh_token").toString());
        authToken.setJti(data.get("jti").toString());
        authToken.setAccessToken(data.get("access_token").toString());
        System.out.println(data);
        return authToken;
    }

    private String httpbasic(String clientId, String clientSecret) {
        String str = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(str.getBytes());
        return "Basic " + new String(encode);
    }
}

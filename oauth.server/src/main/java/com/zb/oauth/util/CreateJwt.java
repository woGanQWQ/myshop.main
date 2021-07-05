package com.zb.oauth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class CreateJwt {
    public static void createToken() {
        //证书文件路径
        String key_location = "myshop.jks";
        //秘钥库密码
        String key_password = "myshop";
        //秘钥密码
        String keypwd = "myshop";
        //秘钥别名
        String alias = "myshop";
        ClassPathResource resource = new ClassPathResource(key_location);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, key_password.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keypwd.toCharArray());
        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> data = new HashMap<>();
        data.put("id", "1001");
        data.put("name", "张三");
        data.put("role", "admin");
        Jwt encode = JwtHelper.encode(JSON.toJSONString(data), new RsaSigner(rsaPrivate));
        String encoded = encode.getEncoded();
        System.out.println(encoded);
    }

    public static void main(String[] args) {
        parseToken();
    }

    public static void parseToken() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4iLCJuYW1lIjoi5byg5LiJIiwiaWQiOiIxMDAxIn0.JbCm2gg83YlhTDmWkapa649afSAFI2U6fQpziSf-mpNmRWgIcgBnsMT8_0NcRwTeowAWnsZNS9djQL0GEwTMZYTt0y90WxqBvRhL11JW5MuVvgqaZlnuEEt8Wz8KqsYf0r7sJhkh9gfYsPSLhOP355z-2MKYgeZYADWi8tPjxsipWOBU6x5zw0HMPcdDaMNEcgGYZWUlDflNwNcaxMXr06GGrHFhABqsA0Pk5GZP4opDA0BiJA-bXYCxjcY_2nRBXFgnO6OvQLrTzRRweflA2Wj4YZqIvbPUULNjG46ABgHINdOf3lpQHsdPKyjeMUxPoYsFNNQY4bNBAG86sBGTKg";
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq/3XQSyTyox7HINT/fkL1v6NM6XjCOqXlFcuDP/6PSM2qphUndodmdBkoq42f72O2qTCdfhMHERYPJZZt+fVNjY3eh5yDFSS3IpwDBFWvyvbKFoa7txjeZMk9BCXxT/A1K/E30hbwCBNe84jJ4JCOvg/aQkmqJ5kyzx+qa6IzfGeSESSPR2tfAo5qMfQPKycIZH3pnP0kCCpe9WaD1GuEowBCpE5Xs5a/sdB5h1o+RA5WjQcdrbveoIW2aQ1DL9mXjw00gmGB6ZWpIN/OD86CVcAguKhQLlN0WTuP6XXroHqxjQ2L/DNe6XeLf5/rMIecr8iUoMeHc+gD0PdE36OzQIDAQAB-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }


}

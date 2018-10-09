package com.dxc.microservices.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

    @Value("${apigw.creds}")
    private String creds;
    
    public String getCreds() {
        return creds;
    }
    
    @Value("${oauth.url}")
    private String oauthUrl;
    
    public String getOauthUrl() {
        return oauthUrl;
    }
    
    @Value("${jwt.signing.key}")
    private String jwtSigningKey;
    
    public String getJwtSigningKey() {
        return jwtSigningKey;
    }
    
    @Value("${jwt.header}")
    private String jwtHeader;
    
    public String getJwtHeader() {
        return jwtHeader;
    }
}

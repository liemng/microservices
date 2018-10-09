package com.dxc.microservices.gateway.filters;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dxc.microservices.gateway.ServiceConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Pre-filter to authenticate Bearer token. Upon authentication, the filter
 * creates a signed JWT token captured in the <code>x-apigw-jwt</code> header
 * for downstream microservices to consume.
 * 
 * @author liemmn
 *
 */
public class OAuth2Filter extends ZuulFilter {
    private static final String FILTER_TYPE = "pre";
    private static Logger log = LoggerFactory.getLogger(OAuth2Filter.class);

    @Autowired
    private RestTemplate rest;

    @Autowired
    private ServiceConfig config;

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String auth = request.getHeader("Authorization");
        // Bail early if we don't have an auth
        if (auth == null) {
            failRequest(ctx, null);
            return null;
        }
        // Validate Bearer token 
        try {
            String token = auth.substring(7);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", config.getCreds());
            HttpEntity<String> entity = new HttpEntity<>(null, headers);  
            ResponseEntity<Map> response = rest.postForEntity(config.getOauthUrl() + token, entity, Map.class);
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            cal.add(Calendar.HOUR, 1);
            Date expiration = cal.getTime();
            // Stuff the response into a JWT token and sign it
            JwtBuilder builder = Jwts.builder()
                    .setIssuer("apigw")
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .setClaims(response.getBody())
                    .signWith(SignatureAlgorithm.HS256, config.getJwtSigningKey().getBytes("UTF-8"));
            ctx.addZuulRequestHeader(config.getJwtHeader(), builder.compact());
        } catch (Throwable t) {
            failRequest(ctx, t);
        }
        return null;
    }

    private void failRequest(RequestContext ctx, Throwable t) {
        log.error("Failed authentication", t);
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
    }

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

}

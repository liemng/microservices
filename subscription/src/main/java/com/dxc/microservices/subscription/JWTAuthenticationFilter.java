package com.dxc.microservices.subscription;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * This filter is responsible for checking the Authorization header for a JWT
 * token and validates the token.
 * 
 * @author liemmn
 *
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    // TODO: Externalize as configurations!
    private static final String SECRET = "hWmZq4t7w!z%C*F-JaNdRgUkXn2r5u8x";
    private static final String HEADER_STRING = "x-apigw-jwt";
    private static final String CLAIM_ROLES = "authorities";
    private static final String CLAIM_USER = "user_name";

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // validate token and extract claims
            Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes("UTF-8")).parseClaimsJws(token).getBody();
            String user = claims.get(CLAIM_USER, String.class);
            @SuppressWarnings("unchecked")
            String role = String.join(",", claims.get(CLAIM_ROLES, List.class));
            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
            if (user != null) {
                LOG.info("JWT claims: " + user + " " + authorityList);
                return new UsernamePasswordAuthenticationToken(user, null, authorityList);
            }
        }
        return null;
    }
}
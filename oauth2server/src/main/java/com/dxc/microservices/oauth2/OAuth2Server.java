package com.dxc.microservices.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OAuth2Server {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2Server.class, args);
    }

    // Allows token introspection, end-point protected by the very same token.  Note that token introspection
    // is also supported by Spring Security via POST /oauth/check_token?token=<token>
    @RequestMapping(value = { "/user" }, produces = "application/json")
    public Object user(OAuth2Authentication user) {
        return (user != null) ? user.getUserAuthentication().getPrincipal() : null;
    }

    // Protect all endpoints except for the health check
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/health").permitAll().anyRequest().authenticated();
        }
    }

    // Configuring the authorization server
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.checkTokenAccess("isAuthenticated()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                .withClient("apps")
                    .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                    .authorities("ROLE_APPS")
                    .scopes("read", "write")
                    .secret("secret").and()
                .withClient("apigw")
                    .authorizedGrantTypes("implicit", "password", "refresh_token")
                    .authorities("ROLE_APIGW")
                    .scopes("read", "write", "trust");
        }
    }

    // Configuring an in-memory user authentication
    @Configuration
    protected static class UserConfig extends WebSecurityConfigurerAdapter {
        
        // The AuthenticationManagerBean is used by Spring Security to handle authentication.
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        // The UserDetailsService is used by Spring Security to handle user information that will be 
        // returned to Spring Security.
        @Override
        @Bean
        public UserDetailsService userDetailsServiceBean() throws Exception {
            return super.userDetailsServiceBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser("elisabet").password("skyline1").roles("USER").and()
                .withUser("liemmn").password("skyline2").roles("USER", "ADMIN");
        }
    }
}
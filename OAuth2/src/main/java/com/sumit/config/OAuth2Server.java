package com.sumit.config;
/**
 * Created by summit on 1/29/17.
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import com.sumit.authService.CustomUserApprovalHandler;
import javax.sql.DataSource;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import javax.naming.NamingException;

import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


@Configuration
@EnableAuthorizationServer
public class OAuth2Server extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    private AuthenticationManager customAuthenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    DataSource mysqlDataSource;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception{
        endpoint.tokenStore(tokenStore()).userApprovalHandler(userApprovalHandler).authenticationManager(customAuthenticationManager);
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {
        client.jdbc(mysqlDataSource);
    }


    @Bean
    public TokenStore tokenStore() throws NamingException {
        return new JdbcTokenStore( mysqlDataSource);
    }
    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws NamingException {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setAccessTokenValiditySeconds(120000);
        defaultTokenServices.setRefreshTokenValiditySeconds(120000);
        return defaultTokenServices;
    }


    @Bean
    @Lazy
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CustomUserApprovalHandler userApprovalHandler() throws Exception{
        CustomUserApprovalHandler customUserApprovalHandler = new CustomUserApprovalHandler();
        customUserApprovalHandler.setApprovalStore(approvalStore());
        customUserApprovalHandler.setClientDetailsService(clientDetailsService);
        customUserApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        customUserApprovalHandler.setUseApprovalStore(true);
        return customUserApprovalHandler;

    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer

                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}

package com.example.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * Created by summit on 6/24/17.
 */

@Configuration
@EnableResourceServer
public class ResourceConfigOAuth extends ResourceServerConfigurerAdapter {

    @Autowired
    private Environment env;


    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"**/test/**").access("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.POST,"/v1/save/**").access("#oauth2.hasScope('write') and hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.PUT,"/bars/**").access("#oauth2.hasScope('read') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')");
        // @formatter:on
    }
    @Primary
    @Bean
    public RemoteTokenServices tokenService() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(env.getProperty("oauth.check_token"));
        tokenService.setClientId(env.getProperty("oauth.clientId"));
        tokenService.setClientSecret(env.getProperty("oauth.secret"));
        return tokenService;
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.resourceId(env.getProperty("oauth.clientId"));
        config.tokenServices(tokenService());
    }

}

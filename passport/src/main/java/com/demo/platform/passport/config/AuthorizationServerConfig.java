package com.demo.platform.passport.config;

import com.demo.platform.passport.service.UserServiceDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author github.com/wanghws
 * @date 20200108
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    AuthenticationManager authenticationManager;
    @Resource
    private RedisConnectionFactory connectionFactory;
    @Resource
    private UserServiceDetailService userServiceDetailService;
    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(accessTokenConverter())
                .userDetailsService(userServiceDetailService)
                .authenticationManager(authenticationManager);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(30));
        tokenServices.setRefreshTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30));
        endpoints.tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("%588&!2(");

        clients.inMemory()
                .withClient("web")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(8*3600)
                .refreshTokenValiditySeconds(8*3600)
                .scopes("web")
                .secret(finalSecret)
                .and()
                .withClient("service")
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .accessTokenValiditySeconds(8*3600)
                .refreshTokenValiditySeconds(8*3600)
                .scopes("service")
                .secret(finalSecret);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("%588&!2(");
        return converter;
    }
}

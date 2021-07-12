package org.ssdlv.categoryservice.utils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ssdlv.categoryservice.blacktoken.BlackTokenService;
import org.ssdlv.categoryservice.utils.filters.JwtAuthorization;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BlackTokenService blackTokenService;

    public SecurityConfig(BlackTokenService blackTokenService) {
        this.blackTokenService = blackTokenService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .addFilterAfter(new JwtAuthorization(blackTokenService), UsernamePasswordAuthenticationFilter.class);
    }
}

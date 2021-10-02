package org.ssdlv.userservice.utils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ssdlv.userservice.blacktoken.BlackTokenService;
import org.ssdlv.userservice.userpermission.UserPermissionRepository;
import org.ssdlv.userservice.users.User;
import org.ssdlv.userservice.users.UserRepository;
import org.ssdlv.userservice.utils.Constants;
import org.ssdlv.userservice.utils.filters.JwtAuthentication;
import org.ssdlv.userservice.utils.filters.JwtAuthorization;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final BlackTokenService blackTokenService;
    private final UserPermissionRepository userPermissionRepository;

    public SecurityConfig(UserRepository userRepository, BlackTokenService blackTokenService, UserPermissionRepository userPermissionRepository) {
        this.userRepository = userRepository;
        this.blackTokenService = blackTokenService;
        this.userPermissionRepository = userPermissionRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            User user = userRepository.findByEmail(email);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            userPermissionRepository.findByUser(user).forEach(userPermission -> {
                authorities.add(new SimpleGrantedAuthority(userPermission.getPermission().getName()));
            });

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        });
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
                .addFilter(new JwtAuthentication(authenticationManager(), userRepository))
                .addFilterAfter(new JwtAuthorization(blackTokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(Constants.AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
    }
}

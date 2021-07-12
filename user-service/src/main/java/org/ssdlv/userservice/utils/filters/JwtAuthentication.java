package org.ssdlv.userservice.utils.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ssdlv.userservice.users.UserController;
import org.ssdlv.userservice.users.UserRepository;

import com.google.gson.Gson;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import org.ssdlv.userservice.users.UserUtil;
import org.ssdlv.userservice.utils.Constants;
import org.ssdlv.userservice.utils.forms.AuthForm;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    public JwtAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        AuthForm authForm = gson.fromJson(reader, AuthForm.class);

        String email = authForm.getEmail();
        String password = authForm.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        List<String> authorities = new ArrayList<>();
        user.getAuthorities().forEach(grantedAuthority -> {
            authorities.add(grantedAuthority.getAuthority());
        });

        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Constants.ACCESS_TOKEN_EXPIRATION)
                .withIssuer(request.getRequestURL().toString())
                .withClaim(Constants.CLAIM_AUTHORITIES, authorities)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Constants.REFRESH_TOKEN_EXPIRATION)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        UserUtil.sendTokenResponse(response, accessToken, refreshToken);
    }
}
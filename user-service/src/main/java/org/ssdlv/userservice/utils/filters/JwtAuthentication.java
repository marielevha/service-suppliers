package org.ssdlv.userservice.utils.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

    public JwtAuthentication(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        BufferedReader reader = request.getReader();
        String headerContentType = request.getHeader("Content-Type");

        String email;
        String password;

        if (headerContentType.contains("application/x-www-form-urlencoded")) {
            String fullText = decode(reader.readLine());
            String[] text = fullText.split("&");
            email = text[0].split("=")[1];
            password = text[1].split("=")[1];
        }
        else  {
            Gson gson = new Gson();
            AuthForm authForm = gson.fromJson(reader, AuthForm.class);

            email = authForm.getEmail();
            password = authForm.getPassword();
        }
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

        org.ssdlv.userservice.users.User localUser = userRepository.findByEmail(user.getUsername());

        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Constants.ACCESS_TOKEN_EXPIRATION)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("id", localUser.getId())
                .withClaim("slug", localUser.getSlug())
                .withClaim("name", localUser.getFirstName() + " " + localUser.getLastName())
                .withClaim(Constants.CLAIM_AUTHORITIES, authorities)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Constants.REFRESH_TOKEN_EXPIRATION)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("id", localUser.getId())
                .sign(algorithm);

        UserUtil.sendTokenResponse(request, response, accessToken, refreshToken, localUser);
    }
}
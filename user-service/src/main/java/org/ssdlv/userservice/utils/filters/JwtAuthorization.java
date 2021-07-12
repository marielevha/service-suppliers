package org.ssdlv.userservice.utils.filters;


import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.ssdlv.userservice.blacktoken.BlackTokenService;
import org.ssdlv.userservice.users.UserRepository;
import org.ssdlv.userservice.utils.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JwtAuthorization extends OncePerRequestFilter {
    private final BlackTokenService blackTokenService;

    public JwtAuthorization(BlackTokenService blackTokenService) {
        this.blackTokenService = blackTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(Constants.PATH_REFRESH_TOKEN)) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorization = request.getHeader(Constants.AUTHORIZATION);

            if (authorization != null && authorization.startsWith(Constants.BEARER)) {
                try {
                    String accessToken = authorization.substring(7);

                    List<String> blackListTokens = blackTokenService.blackListTokens();

                    if (blackListTokens.contains(accessToken)) {
                        response.setHeader("Token-Error-Message", Constants.MESSAGE_INVALID_ACCESS_TOKEN);
                    }
                    else {
                        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);

                        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);

                        String email  = decodedJWT.getSubject();
                        String[] token_authorities = decodedJWT.getClaim(Constants.CLAIM_AUTHORITIES).asArray(String.class);
                        Collection<GrantedAuthority> authorities = new ArrayList<>();


                        for (String authority : token_authorities) {
                            authorities.add(new SimpleGrantedAuthority(authority));
                        }

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(email, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }

                    filterChain.doFilter(request, response);
                }
                catch (Exception e) {
                    response.setHeader("Error", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }
    }
}

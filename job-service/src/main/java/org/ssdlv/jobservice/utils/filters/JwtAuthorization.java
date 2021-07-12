package org.ssdlv.jobservice.utils.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ssdlv.jobservice.blacktoken.BlackTokenService;
import org.ssdlv.jobservice.utils.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthorization extends OncePerRequestFilter {
    private final BlackTokenService blackTokenService;

    public JwtAuthorization(BlackTokenService blackTokenService) {
        this.blackTokenService = blackTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        String accessToken, username;

        if (checkContain(request.getRequestURL().toString())) {
            filterChain.doFilter(request, response);
        }

        else if (authorization != null && authorization.startsWith(Constants.BEARER)) {
            try {
                accessToken = authorization.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);

                List<String> blackListTokens = blackTokenService.blackListTokens();
                if (blackListTokens.contains(accessToken)) {
                    response.setHeader("Token-Error-Message", Constants.MESSAGE_INVALID_ACCESS_TOKEN);
                }
                else {
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);

                    username  = decodedJWT.getSubject();
                    String[] token_authorities = decodedJWT.getClaim(Constants.CLAIM_AUTHORITIES).asArray(String.class);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();


                    for (String authority : token_authorities) {
                        authorities.add(new SimpleGrantedAuthority(authority));
                    }

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                filterChain.doFilter(request, response);
            }
            catch (Exception e) {
                response.setHeader("Error", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                filterChain.doFilter(request, response);
            }
        }
        else {
            response.setHeader("Error", Constants.MESSAGE_ACCESS_TOKEN_REQUIRED);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            filterChain.doFilter(request, response);
        }

    }

    public boolean checkContain(String s) {
        int count = 0;
        for(String content : Constants.AUTH_WHITELIST){
            if (s.contains(content)) {
                count++;
            }
        }
        return count > 0;
    }
}

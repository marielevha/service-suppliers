package org.ssdlv.userservice.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class Constants {
    public static final String SECRET_KEY = "SSDLV4145D184A4B020DD04D7DD59C057192EB1392934A496598DC05EEF18B46A3E6E";
    public static final Date ACCESS_TOKEN_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7));
    public static final Date REFRESH_TOKEN_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 10));

    public static final String AUTHORIZATION = "authorization";
    public static final String BEARER = "Bearer ";
    public static final String PATH_REFRESH_TOKEN = "/refreshToken";
    public static final String PROFILE_CLIENT = "CLIENT";
    public static final String PROFILE_PROVIDER = "PROVIDER";
    public static final String PROFILE_ADMIN = "ADMIN";
    public static final String CLAIM_AUTHORITIES = "authorities";
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String MESSAGE_REFRESH_TOKEN_REQUIRED = "Refresh Token Required.";
    public static final String MESSAGE_INVALID_ACCESS_TOKEN = "Your token is not valid";

    public static final String[] AUTH_WHITELIST = {
            "/login",
            "/loginMvc",
            "/register",
            PATH_REFRESH_TOKEN,
            "/blackListTokens",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "forward:/swagger-ui/index.html",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/h2-console/**",
    };

    public static boolean checkIfContentTypeExist(HttpServletRequest request, String header) {
        String headerContentType = request.getHeader("Content-Type");
        return headerContentType.contains(header);
    }

    public static final String blackToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW52ZXJAc3NkbHYuY29tIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4xNDQ6OTk5OS9sb2dpbiIsImV4cCI6MTYyNjUzMDA5MCwiYXV0aG9yaXRpZXMiOlsiQ0FURUdPUlk6Q1JFQVRFIiwiQ0FURUdPUlk6REVMRVRFIiwiQ0FURUdPUlk6UkVBRCIsIkNBVEVHT1JZOlVQREFURSIsIkpPQjpDUkVBVEUiLCJKT0I6REVMRVRFIiwiSk9COlJFQUQiLCJKT0I6VVBEQVRFIiwiVVNFUjpDUkVBVEUiLCJVU0VSOkRFTEVURSIsIlVTRVI6UkVBRCIsIlVTRVI6VVBEQVRFIl19.aB9NpZeKplNMdhiweapFzgcKgqAF2yFMzHGTN_sWXqs";
}

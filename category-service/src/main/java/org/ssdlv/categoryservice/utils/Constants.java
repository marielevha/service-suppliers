package org.ssdlv.categoryservice.utils;

import java.util.Date;

public class Constants {
    public static String SECRET_KEY = "4145D184A4B020DD04D7DD59C057192EB1392934A496598DC05EEF18B46A3E6E";
    public static Date ACCESS_TOKEN_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
    public static Date REFRESH_TOKEN_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30));

    public static String AUTHORIZATION = "authorization";
    public static String BEARER = "Bearer ";
    public static String PATH_REFRESH_TOKEN = "/refreshToken";

    public static final String[] AUTH_WHITELIST = {
            PATH_REFRESH_TOKEN
    };
}

package org.ssdlv.jobservice.utils;

import java.util.Date;

public class Constants {
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String SECRET_KEY = "SSDLV4145D184A4B020DD04D7DD59C057192EB1392934A496598DC05EEF18B46A3E6E";
    public static final Date ACCESS_TOKEN_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
    public static final Date REFRESH_TOKEN_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30));

    public static final String AUTHORIZATION = "authorization";
    public static final String BEARER = "Bearer ";
    public static final String PATH_REFRESH_TOKEN = "/refreshToken";
    public static final String PUBLISH_KEY = "publish";
    public static final String PUBLISH_NOT_KEY = "not-publish";
    public static final String DELETED_KEY = "deleted";
    public static final String DEFAULT_ORDER_COLUMN = "publishedAt";
    public static final String CLAIM_AUTHORITIES = "authorities";

    public static final String MESSAGE_ACCESS_TOKEN_REQUIRED = "Access Token Required.";
    public static final String MESSAGE_INVALID_ACCESS_TOKEN = "Your token is not valid";

    public static final String[] AUTH_WHITELIST = {
            "/login",
            "/register",
            "/barcodes/**",
            PATH_REFRESH_TOKEN,
            "/swagger-resources",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "forward:/swagger-ui/index.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars",
            "/h2-console",
    };

    public static final String FAKE_DESCRIPTION = "Toffee cookie macaroon. Cake I love carrot cake candy canes I love dragée chocolate cake jelly. Muffin wafer gummies chocolate bar I love donut powder. Liquorice fruitcake lemon drops. Cupcake topping wafer gingerbread ice cream chocolate wafer sugar plum. Icing jujubes pie bonbon fruitcake bear claw icing. Cake sweet roll tiramisu. Tiramisu I love caramels.";

}

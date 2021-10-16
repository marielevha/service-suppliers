package org.ssdlv.userservice.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.ssdlv.userservice.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserUtil {
    public static void sendTokenResponse(HttpServletRequest request, HttpServletResponse response, String accessToken, String refreshToken, User user) throws IOException {
        Map<String, Object> tokens = new HashMap<>();
        tokens.put(Constants.ACCESS_TOKEN_KEY, accessToken);
        tokens.put(Constants.REFRESH_TOKEN_KEY, refreshToken);

        if (user != null) {
            user.setPermissions(null);
            tokens.put("user", user);
        }

        if (request != null) {
            if (Constants.checkIfContentTypeExist(request, "application/x-www-form-urlencoded")) {
                response.setContentType("text/html");
                new ObjectMapper().writeValue(response.getOutputStream(), "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "    <style>" +
                        "        table, th, td {" +
                        "            border: 1px solid black;" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "<h1>Welcome</h1>" +
                        "<p>Please find below your access and refresh tokens</p>" +
                        "<table style='table-layout: fixed; width:100%'>" +
                        "    <tr>" +
                        "        <th>ACCESS TOKEN</th>" +
                        "        <th>REFRESH TOKEN</th>" +
                        "    </tr>" +
                        "    <tr style='height:200px'>" +
                        "        <td style='word-wrap: break-word'>"+accessToken+"</td>" +
                        "        <td style='word-wrap: break-word'>"+refreshToken+"</td>" +
                        "    </tr>" +
                        "</table>" +
                        "</body>" +
                        "</html>");
                return;
            }
        }

        response.setContentType(Constants.CONTENT_TYPE_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}

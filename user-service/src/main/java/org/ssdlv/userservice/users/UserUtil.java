package org.ssdlv.userservice.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ssdlv.userservice.utils.Constants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserUtil {
    public static void sendTokenResponse(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put(Constants.ACCESS_TOKEN_KEY, accessToken);
        tokens.put(Constants.REFRESH_TOKEN_KEY, refreshToken);

        response.setContentType(Constants.CONTENT_TYPE_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}

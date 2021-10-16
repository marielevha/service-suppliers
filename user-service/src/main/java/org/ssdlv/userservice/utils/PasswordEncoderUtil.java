package org.ssdlv.userservice.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderUtil {
    @Bean("password_encoder")
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

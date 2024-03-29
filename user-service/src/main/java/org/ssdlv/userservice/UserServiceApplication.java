package org.ssdlv.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableFeignClients
@SpringBootApplication
@EnableOpenApi
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	/*@Bean("password_encoder")
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}*/

    /*@Autowired
    PasswordEncoder passwordEncoder;*/

}

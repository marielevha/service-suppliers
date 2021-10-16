package org.ssdlv.userservice.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerDocConfig {
    @Bean
    public Docket rest() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("REST API VERSION CODE 1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.ssdlv.userservice"))
                .paths(PathSelectors.any())
                .build().apiInfo(info());
    }

    private ApiInfo info() {
        return new ApiInfo(
                "API DOCUMENTATION FOR USER SERVICE",
                "Description",
                "1.0.0",
                "https://github.com/marielevha",
                new Contact("Mariel Evha ABIR", "https://github.com/marielevha", "evhaabir98@hotmail.com"),
                "Licence Owner",
                "https://github.com/marielevha",
                Collections.emptyList()
        );
    }
}

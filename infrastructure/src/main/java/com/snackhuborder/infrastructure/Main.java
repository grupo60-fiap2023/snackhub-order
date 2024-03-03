package com.snackhuborder.infrastructure;

import com.snackhuborder.infrastructure.configuration.WebServerConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;



@OpenAPIDefinition(info = @Info(title = "Snackhub Order API", version = "1.0.0"))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", in = SecuritySchemeIn.HEADER, bearerFormat = "JWT",
        scheme = "bearer")
@SpringBootApplication
public class Main{
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }
}
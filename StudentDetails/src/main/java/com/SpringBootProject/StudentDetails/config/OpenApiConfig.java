package com.SpringBootProject.StudentDetails.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Madhumitha",
            email = "mitha3300@gmail.com"
        ),
        description = "OpenApi for Spring Security",
        title = "openApi for Spring",
        version = "1.0",
        license = @License(
            name = "licensename",
            url = ""
        ),
        termsOfService = "Terms of Service"
    ),
    servers = {
        @Server(description = "local ENV", url = "http://localhost:8080")
    }
)
public class OpenApiConfig {

}

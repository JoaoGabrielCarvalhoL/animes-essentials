package br.com.carv.essentials.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Depois eu escolho um título").version("1.0").description("Trocar a descricao dps").termsOfService("")
                .contact(new Contact().name("João Gabriel Carvalho").email("27.joaogabriel@gmail.com").url("http://github.com/JoaoGabrielCarvalhoL"))
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

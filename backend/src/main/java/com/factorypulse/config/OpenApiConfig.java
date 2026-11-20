package com.factorypulse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI factoryPulseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FactoryPulse AI — Backend REST API")
                        .description("Secure AIOps-powered observability platform for manufacturing telemetry. Demonstrates on-premise data redaction, zero-knowledge egress proofs, and predictive maintenance intelligence.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FactoryPulse AI Engineering Team")
                                .url("https://github.com/factorypulse/factorypulseai"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

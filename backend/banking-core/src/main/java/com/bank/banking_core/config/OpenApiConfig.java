package com.bank.banking_core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingLedgerAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Banking Core Ledger API")
                        .description("""
                                Event-driven Banking Core built using Spring Boot.
                                
                                Features:
                                - Account Management
                                - Deposit
                                - Withdraw
                                - Money Transfer
                                - Double Entry Ledger
                                - Redis Distributed Locking
                                - Kafka Event Publishing
                                - Kafka Consumers
                                - Retry & Dead Letter Topic
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mahesh")
                                .email("your-email@example.com")
                                .url("https://github.com/Mahesh04022004"))
                        .license(new License()
                                .name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/Mahesh04022004/Banking_Ledger_Core"));
    }
}

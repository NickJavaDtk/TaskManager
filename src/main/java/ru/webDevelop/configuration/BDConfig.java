package ru.webDevelop.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "spring.datasource")
public class BDConfig {
    private String uri;
    private String username;
    private String password;
}

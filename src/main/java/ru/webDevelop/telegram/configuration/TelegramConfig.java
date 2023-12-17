package ru.webDevelop.telegram.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramConfig {
    private String name;
    private String token;
    private String uri;
    private String storage;

}

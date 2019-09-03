package com.cyrillwork.chart.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("chart")
public class MailProperties {
    @Value("${chart.mail.host}")
    private String host;

    @Value("${chart.mail.username}")
    private String username;

    @Value("${chart.mail.password}")
    private String password;

    @Value("${chart.mail.port}")
    private int port;

    @Value("${chart.mail.protocol}")
    private String protocol;

    @Value("${chart.mail.debug}")
    private String debug;
}

package com.cyrillwork.chart.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("server")
public class MainProperties {
    @Value("${chart.server.host}")
    private String host;

    @Value("${chart.server.port}")
    private Integer port;

}

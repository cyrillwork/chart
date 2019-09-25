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

    @Value("${chart.server.greeting}")
    private String greeting;

    @Value("${chart.upload.path}")
    private String uploadPath;

    public boolean isLocalHost(){
        return host.equals("localhost");
    }

}

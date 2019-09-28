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

    @Value("${chart.upload.path}")
    private String uploadPath;

    @Value("${chart.recaptcha}")
    private String recaptcha;

    @Value("${chart.recaptcha.url}")
    private String recaptchaUrl;

    public boolean isLocalHost(){
        return host.equals("localhost");
    }
}

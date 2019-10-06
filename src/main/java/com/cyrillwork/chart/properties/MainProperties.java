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

    @Value("${chart.upload.path.linux}")
    private String uploadPathLinux;

    @Value("${chart.upload.path.windows}")
    private String uploadPathWindows;

    @Value("${chart.recaptcha}")
    private String recaptcha;

    @Value("${chart.recaptcha.url}")
    private String recaptchaUrl;

    public boolean isLocalHost(){
        return host.equals("localhost");
    }

    private String uploadPath = null;

    private boolean isCheckOS = false;

    public String getUploadPath() {
        if(!isCheckOS) {
            isCheckOS = true;
            String property = System.getProperty("os.name");
            if(property.indexOf("Windows") != -1)
                uploadPath = uploadPathWindows;
            else
                uploadPath = uploadPathLinux;
        }
        return uploadPath;
    }

}

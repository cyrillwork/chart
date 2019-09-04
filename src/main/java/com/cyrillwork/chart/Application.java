package com.cyrillwork.chart;

import com.cyrillwork.chart.properties.MailProperties;
import com.cyrillwork.chart.properties.MainProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MailProperties.class, MainProperties.class})
public class Application {

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

}

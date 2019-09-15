package com.cyrillwork.chart;

import com.cyrillwork.chart.controllers.SessionListener;
import com.cyrillwork.chart.properties.MailProperties;
import com.cyrillwork.chart.properties.MainProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties({MailProperties.class, MainProperties.class})
public class ChartApp
{
//    @Bean
//    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
//        ServletListenerRegistrationBean<SessionListener> listenerRegBean =
//                new ServletListenerRegistrationBean<>();
//
//        listenerRegBean.setListener(new SessionListener());
//        return listenerRegBean;
//    }

    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
        System.out.println("ServletListenerRegistrationBean");
        return new ServletListenerRegistrationBean<SessionListener>(new SessionListener());
    }

    public static void main(String[] args) {
        SpringApplication.run(ChartApp.class, args);
    }

}

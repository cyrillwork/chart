package com.cyrillwork.chart;

import com.cyrillwork.chart.controllers.SessionListener;
import com.cyrillwork.chart.properties.MailProperties;
import com.cyrillwork.chart.properties.MainProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties({MailProperties.class, MainProperties.class})
@Slf4j
public class ChartApp
{
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
        log.info("!!!!!!!!!1 ServletListenerRegistrationBean");
        return new ServletListenerRegistrationBean<SessionListener>(new SessionListener());
    }

    public static void main(String[] args) {
        SpringApplication.run(ChartApp.class, args);
    }

}

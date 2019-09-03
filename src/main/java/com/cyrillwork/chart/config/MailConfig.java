package com.cyrillwork.chart.config;

import com.cyrillwork.chart.properties.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Autowired
    private MailProperties mailProperties;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", mailProperties.getProtocol());
        properties.setProperty("mail.debug", mailProperties.getDebug());

        properties.put("mail.smtp.proxy.port", "3128");
        properties.put("mail.smtp.proxy.host", "192.168.92.249");

        return mailSender;
    }
}

package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MailProperties;
import com.cyrillwork.chart.properties.MainProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private MainProperties mainProperties;

    public void send(String email, String subject, String message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

    public void send(User user){

        String href_str = String.format("http://%s:%s/activate/%s",
                mainProperties.getHost(),
                mainProperties.getPort(),
                user.getActivationCode() );

        String href_full = String.format("<a href=\"%s\" target=\"_blank\" rel=\"noopener\">%s</a>",
                href_str, href_str);

        String message = String.format("Привет %s!\n" +
                        "Добро пожаловать в простой чат."+
                        "Для активации пользователя перейдите по ссылке %s",
                user.getUsername(),
                href_full
        );

        //<a href="https://login.qt.io/confirm/aC6uV1dDmY7QqJmcwETuuasCOTHhNRvE" target="_blank" rel="noopener">https://login.qt.io/confirm/aC6uV1dDmY7QqJmcwETuuasCOTHhNRvE</a>

        send(user.getEmail(), "Activation code " + user.getUsername(), message);
    }


}

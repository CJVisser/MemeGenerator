package com.memegenerator.backend.domain.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSender {
    
    @Autowired
    private Environment env;
    /** 
     * @return JavaMailSenderImpl
     */
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.cornevisser.nl");
        mailSender.setPort(587);

        mailSender.setUsername(env.getProperty("MAIL_USER"));
        mailSender.setPassword(env.getProperty("MAIL_PASS"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
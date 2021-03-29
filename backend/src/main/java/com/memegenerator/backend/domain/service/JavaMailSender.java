package com.memegenerator.backend.domain.service;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSender {
    
    
    /** 
     * @return JavaMailSenderImpl
     */
    public JavaMailSenderImpl getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.cornevisser.nl");
        mailSender.setPort(587);

        mailSender.setUsername("javaminor@cornevisser.nl");
        mailSender.setPassword("visser222");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
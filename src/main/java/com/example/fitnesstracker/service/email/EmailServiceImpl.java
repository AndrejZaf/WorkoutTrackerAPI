package com.example.fitnesstracker.service.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendVerificationEmail(String to, String email) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Verify your account registration");
            helper.setFrom("zaftestapp@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch(MessagingException e){
            throw new IllegalStateException("Failed");
        }
    }

    @Override
    @Async
    public void sendForgotPasswordEmail(String to, String email) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Password Reset Requested");
            helper.setFrom("zaftestapp@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch(MessagingException e){
            throw new IllegalStateException("Failed");
        }
    }
}

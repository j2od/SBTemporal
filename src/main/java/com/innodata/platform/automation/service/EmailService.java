package com.innodata.platform.automation.service;

import io.temporal.failure.ApplicationFailure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String senderEmailAddress, String receiverEmailAddress, String emailSubject, String emailBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setTo(receiverEmailAddress);
            helper.setSubject(emailSubject);
            helper.setText(emailBody, true);
            helper.setFrom(senderEmailAddress);
            mailSender.send(message);
        } catch (MailSendException e) {
            System.out.println("REASON: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("ROOT CAUSE: " + e.getCause().getMessage());
            }
            throw new RuntimeException("SMTP Server rejected or failed to send email: " + e.getMessage());
        } catch (MessagingException e) {
            System.out.println("REASON: " + e.getMessage());
            throw new RuntimeException("Temporary network failure communicating with SMTP server", e);
        } catch (Throwable t) {
            System.out.println("REASON: " + t.getMessage());
            throw ApplicationFailure.newNonRetryableFailure("Malformed email payload mapping parameters structure",
                    t.getClass().getName(), t
            );
        }
    }
}

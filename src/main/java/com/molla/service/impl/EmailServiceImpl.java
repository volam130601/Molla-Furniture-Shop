package com.molla.service.impl;

import com.molla.dto.EmailDetails;
import com.molla.dto.StatusCode;
import com.molla.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}") private String sender;

    // Method 1
    // To send a simple email
    @Override
    public String sendSimpleMail(EmailDetails details)
    {
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage  = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getTo());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            // Setting multipart as true for attachments to be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getTo());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));
            if(file.getFilename() != null) {
                mimeMessageHelper.addAttachment(
                        file.getFilename(), file);
                // Sending the mail
                javaMailSender.send(mimeMessage);
                return "Mail sent Successfully";
            } else return "Error while sending mail!!!";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
    @Override
    public Map<String, String> sendHtmlMessage(EmailDetails emailDetails) {
        Map<String, String> report = new HashMap<>();
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(emailDetails.getProperties());
            helper.setFrom(sender);
            helper.setTo(emailDetails.getTo());
            helper.setSubject(emailDetails.getSubject());
            String html = springTemplateEngine.process(emailDetails.getTemplate(), context);
            helper.setText(html, true);

//            log.info("Sending email: {} with html body: {}", emailDetails, html);
            System.out.println("Email is sending....");
            javaMailSender.send(message);
            report.put("message" , "Mail sent Successfully");
            report.put("status" , String.valueOf(StatusCode.SUCCESS));
            return report;
        } catch (MessagingException e) {
            report.put("message" , "Error while sending mail!!!");
            report.put("status" , String.valueOf(StatusCode.FAIL));
            return report;
        }

    }
}

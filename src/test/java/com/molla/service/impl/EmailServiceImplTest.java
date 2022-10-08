package com.molla.service.impl;

import com.molla.dto.EmailDetails;
import com.molla.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceImplTest {
@Autowired
private EmailService emailService;
    @Test
    void sendHtmlMessage() {
        EmailDetails email = new EmailDetails();
        email.setTo("volam13062001@gmail.com");
        email.setSubject("Welcome Email from CodingNConcepts");
        email.setTemplate("web/email-forgot-password.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Ashish");
        properties.put("subscriptionDate", LocalDate.now().toString());
        properties.put("technologies", Arrays.asList("Python", "Go", "C#"));
        email.setProperties(properties);

        Assertions.assertDoesNotThrow(() -> emailService.sendHtmlMessage(email));
    }
}
package com.molla.service;

import com.molla.dto.EmailDetails;

public interface EmailService {
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

    String sendHtmlMessage(EmailDetails emailDetails);
}

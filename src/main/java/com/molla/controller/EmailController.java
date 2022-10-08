package com.molla.controller;

import com.molla.dto.EmailDetails;
import com.molla.dto.ResponseBody;
import com.molla.dto.UserDto;
import com.molla.entity.User;
import com.molla.service.EmailService;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        String status = emailService.sendSimpleMail(emailDetails);
        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }


}

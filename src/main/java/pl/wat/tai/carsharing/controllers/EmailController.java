package pl.wat.tai.carsharing.controllers;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.requests.EmailRequest;
import pl.wat.tai.carsharing.services.GmailServiceImpl;
import pl.wat.tai.carsharing.services.interfaces.GmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@CrossOrigin
@RequestMapping("/api/email")
@RestController
@RequiredArgsConstructor
public class EmailController {

    @PostMapping
    public void sendMail(@RequestBody EmailRequest emailRequest) throws MessagingException, IOException, GeneralSecurityException {
        GmailService gmailService = new GmailServiceImpl(GoogleNetHttpTransport.newTrustedTransport());

        gmailService.sendMessage("mcparkour1337@gmail.com", emailRequest.getName() + " " + emailRequest.getEmail(), emailRequest.getMessage());
    }

}

package pl.wat.tai.carsharing.controllers;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.entities.GmailCredentials;
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

        gmailService.setGmailCredentials(GmailCredentials.builder()
                .userEmail("mcparkour1337@gmail.com")
                .clientId("685363740170-us1mi13qqrff6ktunh3d67b0bsff4h7j.apps.googleusercontent.com")
                .clientSecret("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")
                .accessToken("ya29.A0ARrdaM-i1hBLyDNupW6IwJuZ88tLIAJ2gE_zA0mJIEbvpbWCrBYocAhtweipHzX6WY8f57k3NdfHLWml7WF0zEO9W5to9UGNGCG4jfI9b414v7LQscag0vjOaCdQSLkHR9xFWQFNVKjBkHINb9xRlYiMyyp7YUNnWUtBVEFTQVRBU0ZRRl91NjFWVEZJS3hKVXAzeGZ3X0lmdjJ2Y0JUdw0163")
                .refreshToken("1//04ihoZrjsEJUMCgYIARAAGAQSNwF-L9IrNM09avf-L1H5JoFyh4ZtzKuXOaQYQgmws2V7zkBFCx7kmOgFEKXYRz0io2WcXT_Eb_E")
                .build());

        gmailService.sendMessage("macius3222@gmail.com", emailRequest.getName() + " " + emailRequest.getEmail(), emailRequest.getMessage());
    }

}

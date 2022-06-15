package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.services.GmailCredentials;

import javax.mail.MessagingException;
import java.io.IOException;

public interface GmailService {

        void setGmailCredentials(GmailCredentials gmailCredentials);

        boolean sendMessage(String recipientAddress, String subject, String body) throws MessagingException, IOException;
    }

package pl.wat.tai.carsharing.services.interfaces;

import javax.mail.MessagingException;
import java.io.IOException;

public interface GmailService {
    boolean sendMessage(String recipientAddress, String subject, String body) throws MessagingException, IOException;
}

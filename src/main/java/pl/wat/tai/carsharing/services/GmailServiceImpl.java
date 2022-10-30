package pl.wat.tai.carsharing.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pl.wat.tai.carsharing.DemoApplication;
import pl.wat.tai.carsharing.services.interfaces.GmailService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Collections;
import java.util.Properties;

public final class GmailServiceImpl implements GmailService {

    private static final String APPLICATION_NAME = "CAR RENTAL";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final HttpTransport httpTransport;

    public GmailServiceImpl(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    @Override
    public boolean sendMessage(String recipientAddress, String subject, String body) throws MessagingException,
            IOException {
        Message message = createMessageWithEmail(
                createEmail(recipientAddress, "mcparkour1337@gmail.com", subject, body));

        return createGmail().users()
                .messages()
                .send("mcparkour1337@gmail.com", message)
                .execute()
                .getLabelIds().contains("SENT");
    }

    private Gmail createGmail() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        var email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);

        return new Message()
                .setRaw(Base64.getEncoder().encodeToString(buffer.toByteArray()));
    }

    private Credential authorize() throws IOException {
        var clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(DemoApplication.class.getResourceAsStream("/client_secrets.json")));

        var flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(GmailScopes.GMAIL_SEND))
                .setAccessType("offline")
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .build();

        var serverReceiver = new LocalServerReceiver
                .Builder().setPort(8001).build();

        return new AuthorizationCodeInstalledApp(flow, serverReceiver).authorize("user");
    }

}
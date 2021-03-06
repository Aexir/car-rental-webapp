package pl.wat.tai.carsharing.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pl.wat.tai.carsharing.data.entities.GmailCredentials;
import pl.wat.tai.carsharing.services.interfaces.GmailService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public final class GmailServiceImpl implements GmailService {

    private static final String APPLICATION_NAME = "CAR RENTAL";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final HttpTransport httpTransport;
    private GmailCredentials gmailCredentials;

    public GmailServiceImpl(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    @Override
    public String[] getAccessToken() {
        return new String[0];
    }

    @Override
    public void setGmailCredentials(GmailCredentials gmailCredentials) {
        this.gmailCredentials = gmailCredentials;
    }

    @Override
    public boolean sendMessage(String recipientAddress, String subject, String body) throws MessagingException,
            IOException {
        Message message = createMessageWithEmail(
                createEmail(recipientAddress, gmailCredentials.getUserEmail(), subject, body));

        return createGmail().users()
                .messages()
                .send(gmailCredentials.getUserEmail(), message)
                .execute()
                .getLabelIds().contains("SENT");
    }

    private Gmail createGmail() {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
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
                .setRaw(Base64.encodeBase64URLSafeString(buffer.toByteArray()));
    }

    public String getNewToken(String refreshToken, String clientId, String clientSecret) throws IOException {
        ArrayList<String> scopes = new ArrayList<>();

        scopes.add("https://www.googleapis.com/auth/gmail.send");

        TokenResponse tokenResponse = new GoogleRefreshTokenRequest(new NetHttpTransport(), new GsonFactory(),
                refreshToken, clientId, clientSecret).setScopes(scopes).setGrantType("refresh_token").execute();

        return tokenResponse.getAccessToken();
    }

    private Credential authorize() {
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(gmailCredentials.getClientId(), gmailCredentials.getClientSecret())
                .build()
                .setAccessToken(gmailCredentials.getAccessToken())
                .setRefreshToken(gmailCredentials.getRefreshToken());
    }

}
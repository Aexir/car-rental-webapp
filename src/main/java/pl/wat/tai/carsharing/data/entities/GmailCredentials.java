package pl.wat.tai.carsharing.data.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
public class GmailCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userEmail;
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String refreshToken;
}
package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.wat.tai.carsharing.data.requests.LoginRequest;
import pl.wat.tai.carsharing.data.requests.SignupRequest;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;


public interface AuthService {
    ResponseEntity<?> logoutUser();

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signUpRequest) throws GeneralSecurityException, IOException, MessagingException;

    ResponseEntity<?> confirmUser(String token);
}

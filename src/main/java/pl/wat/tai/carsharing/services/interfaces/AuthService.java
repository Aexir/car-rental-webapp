package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.wat.tai.carsharing.data.requests.LoginRequest;
import pl.wat.tai.carsharing.data.requests.SignupRequest;


public interface AuthService {
    ResponseEntity<?> logoutUser();

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signUpRequest);

    ResponseEntity<?> confirmUser(String token);
}

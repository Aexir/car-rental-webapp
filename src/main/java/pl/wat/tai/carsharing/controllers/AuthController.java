package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.requests.LoginRequest;
import pl.wat.tai.carsharing.data.requests.SignupRequest;
import pl.wat.tai.carsharing.data.response.MessageResponse;
import pl.wat.tai.carsharing.services.interfaces.AuthService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            return authService.registerUser(signUpRequest);
        } catch (GeneralSecurityException | IOException | MessagingException e) {

            return ResponseEntity.badRequest().body(new MessageResponse("Cannot register user"));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return authService.logoutUser();
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        return authService.confirmUser(token);
    }
}

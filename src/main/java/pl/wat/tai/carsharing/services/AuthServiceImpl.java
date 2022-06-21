package pl.wat.tai.carsharing.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.wat.tai.carsharing.data.entities.GmailCredentials;
import pl.wat.tai.carsharing.data.entities.Role;
import pl.wat.tai.carsharing.data.entities.User;
import pl.wat.tai.carsharing.data.entities.VerificationToken;
import pl.wat.tai.carsharing.data.entities.enums.ERole;
import pl.wat.tai.carsharing.data.requests.LoginRequest;
import pl.wat.tai.carsharing.data.requests.SignupRequest;
import pl.wat.tai.carsharing.data.response.JwtResponse;
import pl.wat.tai.carsharing.data.response.MessageResponse;
import pl.wat.tai.carsharing.repositories.RoleRepository;
import pl.wat.tai.carsharing.repositories.TokenRepository;
import pl.wat.tai.carsharing.repositories.UserRepository;
import pl.wat.tai.carsharing.services.interfaces.AuthService;
import pl.wat.tai.carsharing.services.interfaces.GmailService;
import pl.wat.tai.carsharing.utils.JwtUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final TokenRepository tokenRepository;
    private final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,40}$";
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public ResponseEntity<?> logoutUser() {
//        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .body(new MessageResponse("You've been signed out!"));
        return null;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) throws GeneralSecurityException, IOException, MessagingException {
        if (signUpRequest.getUsername().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username cannot be null!"));
        }

        if (!signUpRequest.getPassword().matches(PASSWORD_PATTERN)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password must contain lowercase letter, uppercase letter, number and special character and between 6 and 40"));
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setBirthDate(signUpRequest.getBirthDate());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());


        Set<Role> roles = new HashSet<>();
        Role accountStatus = roleRepository.findByName(ERole.ROLE_INACTIVE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(accountStatus);

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found." + ERole.ROLE_USER));
        roles.add(userRole);


        user.setRoles(roles);

        String token = UUID.randomUUID().toString();

        String confirmationUrl
                = "http://localhost:8080/api/auth/confirm?token=" + token;


        VerificationToken token1 = new VerificationToken();
        token1.setToken(token);
        token1.setUser(user);

        tokenRepository.save(token1);

        GmailService gmailService = new GmailServiceImpl(GoogleNetHttpTransport.newTrustedTransport());

//        String[] cred = gmailService.getAccessToken();
//
//
//        String accessToken = cred[0];
//        String refreshCode = cred[1];

        gmailService.setGmailCredentials(GmailCredentials.builder()
                .userEmail("mcparkour1337@gmail.com")
                .clientId("685363740170-us1mi13qqrff6ktunh3d67b0bsff4h7j.apps.googleusercontent.com")
                .clientSecret("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")
                .accessToken("ya29.A0ARrdaM-i1hBLyDNupW6IwJuZ88tLIAJ2gE_zA0mJIEbvpbWCrBYocAhtweipHzX6WY8f57k3NdfHLWml7WF0zEO9W5to9UGNGCG4jfI9b414v7LQscag0vjOaCdQSLkHR9xFWQFNVKjBkHINb9xRlYiMyyp7YUNnWUtBVEFTQVRBU0ZRRl91NjFWVEZJS3hKVXAzeGZ3X0lmdjJ2Y0JUdw0163")
                .refreshToken("1//04ihoZrjsEJUMCgYIARAAGAQSNwF-L9IrNM09avf-L1H5JoFyh4ZtzKuXOaQYQgmws2V7zkBFCx7kmOgFEKXYRz0io2WcXT_Eb_E")
                .build());

        gmailService.sendMessage(user.getEmail(), "Confirm registration", "Confirm your account: " + confirmationUrl);


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> confirmUser(String token) {

        VerificationToken token1 = tokenRepository.findByToken(token);

        if (new Date().after(token1.getExipiryDate())) {
            return ResponseEntity.ok(new MessageResponse("Your activation token expired"));
        }
        User user = userRepository.getReferenceById(token1.getUser().getId());
        user.getRoles().removeIf(x -> x.getName() == ERole.ROLE_INACTIVE);
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_ACTIVE).get());

        userRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000/login")).build();
    }
}

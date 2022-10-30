package pl.wat.tai.carsharing.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
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
import pl.wat.tai.carsharing.DemoApplication;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
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

        GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(DemoApplication.class.getResourceAsStream("/client_secrets.json")));


        if (gmailService.sendMessage(user.getEmail(), "Confirm registration", "Confirm your account: " + confirmationUrl)) {
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("CHUJNIA"));
        }
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

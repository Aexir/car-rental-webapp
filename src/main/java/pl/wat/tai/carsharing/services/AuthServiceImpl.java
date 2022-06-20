package pl.wat.tai.carsharing.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

        // Create new user's account
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
                .accessToken("ya29.A0ARrdaM85OjwVxoQ2LKjst0PfWqeBOQ5OJGevTLHR6MtuZ0kLor248e4x3BWN41hv01iPShz2MQ4ZcCWCGD-MtXY1aRVZMQrkx_5jn-wMB4tnguHlqtQDCSiNSfusSTX37Zdh7-K1k3BE5Hz7bAKxdJYB-ydXYUNnWUtBVEFTQVRBU0ZRRl91NjFWVzhSUjljMnZaRm9Sd3dGV29vb0ZqZw0163")
                .refreshToken("1//0cRUPQ4-nIOOBCgYIARAAGAwSNwF-L9IrrMcyK4UeftbzixeiAuSZ7aZFNkyOdnsEpOPwHjSNPk05-xU87-x2nBPGj6YcAWJ-OBI")
                .build());

        gmailService.sendMessage(user.getEmail(), "Confirm registration", "Confirm your account: " + confirmationUrl);

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(user.getEmail());
//        email.setSubject("ACCOUNT ACTIVATION");
//        email.setText("ACTIVATE YOUR ACCOUNT:   " + "\r\n" + "http://localhost:8080" + confirmationUrl);
//        emailService.sendEmail(email);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> confirmUser(String token) {

        VerificationToken token1 = tokenRepository.findByToken(token);


        User user = userRepository.getReferenceById(token1.getUser().getId());
        user.getRoles().removeIf(x -> x.getName() == ERole.ROLE_INACTIVE);
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_ACTIVE).get());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("SIEMA POTWIERDZAM"));
    }
}

package pl.wat.tai.carsharing.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.entities.enums.ERole;
import pl.wat.tai.carsharing.data.entities.Role;
import pl.wat.tai.carsharing.data.entities.User;
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


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role accountStatus = roleRepository.findByName(ERole.ROLE_INACTIVE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(accountStatus);

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        String confirmationUrl
                = "http://localhost:8080/confirm?token=" + token;


        GmailService gmailService = new GmailServiceImpl(GoogleNetHttpTransport.newTrustedTransport());
        gmailService.setGmailCredentials(GmailCredentials.builder()
                .userEmail("mcparkour1337@gmail.com")
                .clientId("685363740170-us1mi13qqrff6ktunh3d67b0bsff4h7j.apps.googleusercontent.com")
                .clientSecret("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")
                .accessToken("ya29.a0ARrdaM8e34zK-L8iN6c5y59av-OnmBxt537YRMu1Hy7yibS9LQ4fyX48qxFJ9mDDCgOANUET73MPLtIEMprmF5c8N5uAm6mU16y4NAnDFb1ecsDDHXjyc707a0QTgNdNSOzkIUxf2UO4NYJC4XivY-gB-MOc")
                .refreshToken("1//0cXq9ETZ4UH17CgYIARAAGAwSNwF-L9Irg62UoLKtsxlrRkPNgBxeZlCzTMCtaQMvXe-YhYFfwdOqQgJ-3NOEWaLB8FL2rKxbcnM")
                .build());

        gmailService.sendMessage("mcparkour1337@gmail.com", "Subject", "body text");

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(user.getEmail());
//        email.setSubject("ACCOUNT ACTIVATION");
//        email.setText("ACTIVATE YOUR ACCOUNT:   " + "\r\n" + "http://localhost:8080" + confirmationUrl);
//        emailService.sendEmail(email);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> confirmUser(String token) {
        return null;
    }
}

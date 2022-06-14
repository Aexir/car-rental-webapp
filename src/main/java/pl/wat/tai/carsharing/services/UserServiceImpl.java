package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.entities.Role;
import pl.wat.tai.carsharing.data.entities.User;
import pl.wat.tai.carsharing.data.entities.enums.ERole;
import pl.wat.tai.carsharing.data.requests.SignupRequest;
import pl.wat.tai.carsharing.data.requests.UpdatePasswordRequest;
import pl.wat.tai.carsharing.data.requests.UpdateRequest;
import pl.wat.tai.carsharing.data.response.JwtResponse;
import pl.wat.tai.carsharing.data.response.MessageResponse;
import pl.wat.tai.carsharing.data.response.UserResponse;
import pl.wat.tai.carsharing.mappers.UserMapper;
import pl.wat.tai.carsharing.repositories.UserRepository;
import pl.wat.tai.carsharing.services.interfaces.UserService;
import pl.wat.tai.carsharing.utils.JwtUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    public final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(userMapper::toResponseMapper).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> updateUserInfo(UpdateRequest updateRequest) {

        if (!updateRequest.getCurrentUsername().contains(updateRequest.getUsername()))
        {
            if (userRepository.existsByUsername(updateRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }
        }
        User user = userRepository.findByUsername(updateRequest.getCurrentUsername()).get();

        if (!user.getEmail().contains(updateRequest.getEmail())){
            if (userRepository.existsByEmail(updateRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }
        }

        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPassword(encoder.encode(updateRequest.getPassword()));
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(updateRequest.getUsername(), updateRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<?> updateUserPassword(UpdatePasswordRequest updatePasswordRequest) {
        User user = userRepository.findByUsername(updatePasswordRequest.getUsername()).get();
        String currentPassword = encoder.encode(updatePasswordRequest.getCurrentPassword());
        String newPassword = encoder.encode(updatePasswordRequest.getNewPassword());
        System.out.println(updatePasswordRequest.getNewPassword());
        System.out.println(updatePasswordRequest.getCurrentPassword());

        System.out.println(currentPassword);
        System.out.println(user.getPassword());
        System.out.println(newPassword);
        if (!user.getPassword().contains(currentPassword)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password not match!"));
        }
        if (currentPassword.contains(newPassword)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: New password contains old password!"));
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), newPassword));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        return ResponseEntity.ok(jwtResponse);
    }
}

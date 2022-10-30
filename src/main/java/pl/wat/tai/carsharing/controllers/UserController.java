package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.requests.UpdatePasswordRequest;
import pl.wat.tai.carsharing.data.requests.UpdateRequest;
import pl.wat.tai.carsharing.data.response.UserResponse;
import pl.wat.tai.carsharing.services.interfaces.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


    @PatchMapping(value = "/updateProfile", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserInfo(@Valid @RequestBody UpdateRequest updateRequest) {
        return userService.updateUserInfo(updateRequest);
    }

    @PutMapping("/updatePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return userService.updateUserPassword(updatePasswordRequest);
    }


}

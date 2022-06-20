package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.requests.UpdatePasswordRequest;
import pl.wat.tai.carsharing.data.requests.UpdateRequest;
import pl.wat.tai.carsharing.data.response.UserResponse;
import pl.wat.tai.carsharing.services.interfaces.UserService;

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


    @PostMapping(value = "/updateProfile", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserInfo(@RequestBody UpdateRequest updateRequest) {
        return userService.updateUserInfo(updateRequest);
    }

    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return userService.updateUserPassword(updatePasswordRequest);
    }


}

package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/updateProfile")
    @PreAuthorize("hasRole('USER')")
    public void updateUserInfo(@RequestBody UpdateRequest updateRequest){
        userService.updateUserInfo(updateRequest);
    }

    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('USER')")
    public void updatePassword(){

    }


}

package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.UserRequest;
import pl.wat.tai.carsharing.data.UserRespone;
import pl.wat.tai.carsharing.services.interfaces.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserRespone> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody UserRequest userRequest){
        userService.saveUser(userRequest);
    }
}

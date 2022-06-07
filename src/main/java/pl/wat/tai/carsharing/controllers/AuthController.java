package pl.wat.tai.carsharing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public void signupUser(){

    }

    @PostMapping("/signin")
    public void signInUser(){

    }

    @PostMapping("/signout")
    public void signOutUseR(){

    }
}

package pl.wat.tai.carsharing.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.services.PayUOrderService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin(allowedHeaders = "*", origins = {"http://localhost:3000", "http://localhost:8080/api/payu"})
@RequestMapping("/api")
@RequiredArgsConstructor
public class PayUController {

    private final PayUOrderService orderService;

    @PostMapping("/payu")
    public ResponseEntity<?> handleCheckout(@RequestBody RentRequest rentRequest, HttpServletRequest request) {
        return orderService.handleCheckout(rentRequest, request);
    }
}

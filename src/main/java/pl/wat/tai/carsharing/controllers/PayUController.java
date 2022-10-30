package pl.wat.tai.carsharing.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.MessageResponse;
import pl.wat.tai.carsharing.services.PayUOrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

    @GetMapping("/payu-callback")
    public ResponseEntity<?> handlePaymentCallback(@RequestParam Optional<String> error, HttpServletRequest request) {
        return ResponseEntity.ok(new MessageResponse(error.orElse("SUCCESS")));
    }
}

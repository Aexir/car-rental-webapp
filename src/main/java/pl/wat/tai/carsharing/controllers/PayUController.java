package pl.wat.tai.carsharing.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.config.PayUConfigurationProperties;
import pl.wat.tai.carsharing.data.requests.OrderCreateRequest;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.OrderCreateResponse;
import pl.wat.tai.carsharing.payu.*;
import pl.wat.tai.carsharing.payu.service.PayUOrderService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;
import java.util.Objects;

import static pl.wat.tai.carsharing.data.response.OrderCreateResponse.Status.STATUS_CODE_SUCCESS;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
public class PayUController {

    private final PayUOrderService orderService;

    @PostMapping("/payu")
    public ResponseEntity<?> handleCheckout(@RequestBody RentRequest rentRequest, HttpServletRequest request) {
        return orderService.handleCheckout(rentRequest, request);
    }
}

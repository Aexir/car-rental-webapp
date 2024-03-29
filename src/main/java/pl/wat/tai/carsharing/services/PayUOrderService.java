package pl.wat.tai.carsharing.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import pl.wat.tai.carsharing.config.PayUConfigurationProperties;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.Payment;
import pl.wat.tai.carsharing.data.entities.User;
import pl.wat.tai.carsharing.data.requests.OrderCreateRequest;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.MessageResponse;
import pl.wat.tai.carsharing.data.response.OrderCreateResponse;
import pl.wat.tai.carsharing.payu.Product;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.repositories.PaymentRepository;
import pl.wat.tai.carsharing.repositories.UserRepository;
import pl.wat.tai.carsharing.services.interfaces.RentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static pl.wat.tai.carsharing.data.response.OrderCreateResponse.Status.STATUS_CODE_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayUOrderService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PayUConfigurationProperties payUConfiguration;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final RentService rentService;
    private final PaymentRepository paymentRepository;

    @Resource(name = "payuApiRestTemplate")
    private RestTemplate restTemplate;

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public ResponseEntity<?> handleCheckout(@RequestBody RentRequest rentRequest, HttpServletRequest request) {

        long daysBetween = getDifferenceDays(rentRequest.getStartDate(), rentRequest.getEndDate());

        Car car = carRepository.getReferenceById(rentRequest.getCarId());
        User user = userRepository.getReferenceById(rentRequest.getUserId());

        float totalAmount = daysBetween * car.getPrice();

        String productName = car.getBrand() + " " + car.getModel();
        float unitPrice = totalAmount;
        String email = user.getEmail();

        //OrderCreateRequest orderRequest = prepareOrderCreateRequest(totalAmount, productName, unitPrice, email, request);
        OrderCreateRequest orderRequest = prepareOrderCreateRequest(String.valueOf((int) totalAmount), productName, String.valueOf(unitPrice), email, request);

        OrderCreateResponse orderResponse = order(orderRequest);

        if (!orderResponse.getStatus().getStatusCode().equals(STATUS_CODE_SUCCESS)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Payment failed! "));
        }
        Payment p = new Payment();
        p.setUserId(rentRequest.getUserId());
        p.setOrderId(orderResponse.getOrderId());
        p.setCarId(rentRequest.getCarId());
        p.setEndDate(rentRequest.getEndDate());
        p.setStartDate(rentRequest.getStartDate());
        p.setEndShowroomName(rentRequest.getEndShowroomName());
        p.setShowroomName(rentRequest.getShowroomName());
        paymentRepository.save(p);
        System.out.println((rentRequest));
        //rentService.createRent(rentRequest);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(orderResponse.getRedirectUri())).build();
    }


    private OrderCreateRequest prepareOrderCreateRequest(String totalAmount, String productName, String unitPrice, String email, final HttpServletRequest request) {
        String unitPrice1 = Objects.nonNull(totalAmount)
                ? String.valueOf(100 * Integer.parseInt(totalAmount))
                : "2500";
        return OrderCreateRequest.builder()
                .customerIp(request.getRemoteAddr())
                .merchantPosId(payUConfiguration.getMerchantPosId())
                .description(payUConfiguration.getDescription())
                .currencyCode("PLN")
                .totalAmount(
                        unitPrice1
                ).products(
                        Collections.singletonList(
                                Product.builder()
                                        .name(
                                                productName
                                        ).quantity("1")
                                        .unitPrice(
                                                unitPrice1
                                        ).build()
                        )).email(email).locale("pl").build();
    }


    @SneakyThrows
    public OrderCreateResponse order(final OrderCreateRequest orderCreateRequest) {
        orderCreateRequest.setContinueUrl("http://localhost:3000/payu-callback");


        final ResponseEntity<String> jsonResponse = restTemplate.postForEntity(payUConfiguration.getOrderUrl(), orderCreateRequest, String.class);

        log.info("Response as String = {}", jsonResponse.getBody());
        return objectMapper.readValue(jsonResponse.getBody(), OrderCreateResponse.class);
    }
}

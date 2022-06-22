package pl.wat.tai.carsharing.data.requests;

import lombok.Builder;
import lombok.Data;
import pl.wat.tai.carsharing.data.entities.User;
import pl.wat.tai.carsharing.payu.Product;

import java.util.List;

@Data
@Builder
public class OrderCreateRequest {
    private String continueUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String totalAmount;
    private String email;
    private String locale;
    private List<Product> products;
}
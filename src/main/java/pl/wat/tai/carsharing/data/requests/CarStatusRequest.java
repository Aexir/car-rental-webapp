package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

@Data
public class CarStatusRequest {

    private long id;
    private String status;
}

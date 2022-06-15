package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import javax.validation.Valid;

@Data
public class CarStatusRequest {

    private long id;
    private String status;
}

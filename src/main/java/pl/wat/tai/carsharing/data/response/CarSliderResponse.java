package pl.wat.tai.carsharing.data.response;

import lombok.Data;

@Data
public class CarSliderResponse {
    private long carId;
    private String brand;
    private String model;
    private String engine;
    private String url;
}

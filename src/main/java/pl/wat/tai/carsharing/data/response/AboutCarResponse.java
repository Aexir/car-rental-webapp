package pl.wat.tai.carsharing.data.response;

import lombok.Data;

@Data
public class AboutCarResponse {
    private long carId;
    private String brand;
    private String model;
    private String engine;
    private String url;
    private float price;

    private String transmission;
    private int seats;
    private String carType;
    private String fuelType;
}

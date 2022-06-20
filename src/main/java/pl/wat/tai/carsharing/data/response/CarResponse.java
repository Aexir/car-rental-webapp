package pl.wat.tai.carsharing.data.response;

import lombok.Data;

@Data
public class CarResponse {
    private long id;
    private String brand;//
    private String model;//
    private int seats;//
    private String transmission;//
    private String engine;
    private float price;
    private String plate;
    private String vin;


    private String fuel;
    private String carType;
    private String carImageUrl;
    private String carStatus;
}

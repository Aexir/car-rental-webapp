package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

@Data
public class CarRequest {
    private String brand;
    private String model;
    private int seats;
    private String transmission;
    private String fuel;
    private String carType;
   // private byte[] carImage;

}

package pl.wat.tai.carsharing.data.requests;

import lombok.Data;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.entities.CarStatus;
import pl.wat.tai.carsharing.data.entities.CarType;
import pl.wat.tai.carsharing.data.entities.Fuel;

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

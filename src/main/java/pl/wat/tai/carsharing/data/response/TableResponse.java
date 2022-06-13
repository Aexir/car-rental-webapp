package pl.wat.tai.carsharing.data.response;

import lombok.Data;

@Data
public class TableResponse {
    private String url;
    private String showroom;
    private String brand;
    private String model;
    private String year;
    private String type;
    private int seats;
    private String transmission;
    private String fuel;
}

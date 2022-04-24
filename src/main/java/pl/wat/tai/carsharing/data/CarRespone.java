package pl.wat.tai.carsharing.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarRespone {
    private long carId;
    private String manufacturer;
    private String model;
    private String fuelType;
    private float fuelTank;
    private float fuelTankSize;
    private boolean inUse;
    private long latitude;
    private long altitude;
}

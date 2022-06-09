package pl.wat.tai.carsharing.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarResponse {
    private long carId;
    private String producer;
    private String model;
    private float currentFuelTank;
    private float fullTankSize;
    private boolean inUse;
}

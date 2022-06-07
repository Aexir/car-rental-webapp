package pl.wat.tai.carsharing.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.CarResponse;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.repositories.CarRepository;

@Component
@RequiredArgsConstructor
public class CarMapper {
    private final CarRepository carRepository;

    public CarResponse carToCarRespone(Car car) {
        CarResponse carResponse = new CarResponse();
        carResponse.setCarId(car.getCarId());
        carResponse.setFuelTank(car.getFuelTank());
        carResponse.setFuelTankSize(car.getFuelTankSize());
        //carRespone.setManufacturer(car.getManufacturer());
        carResponse.setModel(car.getModel());
        carResponse.setInUse(car.isInUse());
        carResponse.setFuelType(car.getFuelType());
        carResponse.setAltitude(car.getAltitude());
        carResponse.setLatitude(car.getLatitude());
        return carResponse;
    }
}

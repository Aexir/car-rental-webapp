package pl.wat.tai.carsharing.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.CarRespone;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.repositories.CarRepository;

@Component
@RequiredArgsConstructor
public class CarMapper {
    private final CarRepository carRepository;

    public CarRespone carToCarRespone(Car car){
        CarRespone carRespone = new CarRespone();
        carRespone.setCarId(car.getCarId());
        carRespone.setFuelTank(car.getFuelTank());
        carRespone.setFuelTankSize(car.getFuelTankSize());
        //carRespone.setManufacturer(car.getManufacturer());
        carRespone.setModel(car.getModel());
        carRespone.setInUse(car.isInUse());
        carRespone.setFuelType(car.getFuelType());
        carRespone.setAltitude(car.getAltitude());
        carRespone.setLatitude(car.getLatitude());
        return carRespone;
    }
}

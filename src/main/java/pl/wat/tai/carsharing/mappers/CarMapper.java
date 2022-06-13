package pl.wat.tai.carsharing.mappers;

import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.response.CarResponse;

@Component
public class CarMapper {
    public CarResponse carToResponse(Car car){
        CarResponse carResponse = new CarResponse();
        carResponse.setCarId(car.getId());
        carResponse.setYear(car.getYear());
        carResponse.setCurrentFuelTank(0);
        carResponse.setModel(car.getModel());
        carResponse.setProducer(car.getBrand());
        carResponse.setCarStatus("car.getCarStatus().getName().toString()");
        return carResponse;
    }
}

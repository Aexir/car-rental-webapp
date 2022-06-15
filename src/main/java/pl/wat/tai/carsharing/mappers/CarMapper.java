package pl.wat.tai.carsharing.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.enums.EFuel;
import pl.wat.tai.carsharing.data.response.AboutCarResponse;
import pl.wat.tai.carsharing.data.response.CarResponse;
import pl.wat.tai.carsharing.repositories.FuelTypesRepository;

@Component
@RequiredArgsConstructor
public class CarMapper {

    private final FuelTypesRepository fuelTypesRepository;
    private final CarImageMapper carImageMapper;

    public AboutCarResponse carToAbout(Car car){
        AboutCarResponse aboutCarResponse = new AboutCarResponse();
        aboutCarResponse.setCarId(car.getId());
        aboutCarResponse.setUrl(carImageMapper.mapToFileResponse(car.getCarImage()).getUrl());
        aboutCarResponse.setBrand(car.getBrand());
        aboutCarResponse.setEngine(car.getEngine());
        aboutCarResponse.setModel(car.getModel());
        aboutCarResponse.setPrice(car.getPrice());

        aboutCarResponse.setTransmission(car.getTransmission());
        aboutCarResponse.setSeats(car.getSeats());
        aboutCarResponse.setCarType(car.getCarType().getName().toString());
        aboutCarResponse.setFuelType(car.getFuel().getName().toString());
        return aboutCarResponse;
    }


    public CarResponse carToResponse(Car car){
        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setBrand(car.getBrand());
        carResponse.setModel(car.getModel());
        carResponse.setSeats(car.getSeats());
        carResponse.setTransmission(car.getTransmission());
        carResponse.setEngine(car.getEngine());
        carResponse.setPrice(car.getPrice());
        carResponse.setPlate(car.getPlate());
        carResponse.setVin(car.getVin());

        carResponse.setFuel(car.getFuel().getName().toString());
        carResponse.setCarType(car.getCarType().getName().toString());
        carResponse.setCarImageUrl(carImageMapper.mapToFileResponse(car.getCarImage()).getUrl());
        carResponse.setCarStatus(car.getCarStatus().getName().toString());

        return carResponse;
    }
}

package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.requests.CarRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;

import java.util.List;

public interface CarService {
    void addNewCar(CarRequest carRequest);

    List<CarResponse> getAllCars();
}

package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.CarResponse;

import java.util.List;

public interface CarService {
    List<CarResponse> getAllCars();

    List<CarResponse> getUniqueModels();
}

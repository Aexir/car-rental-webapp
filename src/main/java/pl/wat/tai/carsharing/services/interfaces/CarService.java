package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.requests.CarRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;

import java.util.List;

public interface CarService {

    void addNewCar(CarRequest carRequest);
    void addNewCar(CarRequest carRequest, MultipartFile file);
    void addNewCar(String brand, String model, int seats, String transmission, String fuel, String carType, MultipartFile file);

    List<CarResponse> getAllCars();
}

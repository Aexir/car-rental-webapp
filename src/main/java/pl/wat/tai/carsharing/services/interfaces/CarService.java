package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.requests.CarRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;

import java.util.List;

public interface CarService {

    void addNewCar(CarRequest carRequest);
    public void addNewCar(CarRequest carRequest, MultipartFile file);
    List<CarResponse> getAllCars();
}

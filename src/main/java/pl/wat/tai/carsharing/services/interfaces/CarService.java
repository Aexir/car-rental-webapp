package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.requests.CarStatusRequest;
import pl.wat.tai.carsharing.data.response.AboutCarResponse;
import pl.wat.tai.carsharing.data.response.CarResponse;

import java.util.List;

public interface CarService {

    void addNewCar(String brand, String model, int seats, String transmission, String fuel, String carType, MultipartFile file, String engine, String plate, String vin, float price);
    List<CarResponse> getAllCars();
    AboutCarResponse aboutCar(long id);
    void editCar(long id, String carStatus, MultipartFile file, String plate, Float price);
    void setCarStatus(CarStatusRequest carStatusRequest);

    void removeCar(long id);
}

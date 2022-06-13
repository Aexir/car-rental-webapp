package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.entities.CarStatus;
import pl.wat.tai.carsharing.data.entities.enums.ECarStatus;
import pl.wat.tai.carsharing.data.entities.enums.ECarType;
import pl.wat.tai.carsharing.data.entities.enums.EFuel;
import pl.wat.tai.carsharing.data.requests.CarRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;
import pl.wat.tai.carsharing.mappers.CarMapper;
import pl.wat.tai.carsharing.repositories.*;
import pl.wat.tai.carsharing.services.interfaces.CarImageService;
import pl.wat.tai.carsharing.services.interfaces.CarService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarImageRepository carImageRepository;
    private final CarStatusRepository carStatusRepository;
    private final CarTypesRepository carTypesRepository;
    private final FuelTypesRepository fuelTypesRepository;
    private final CarImageServiceImpl carImageService;
    private final CarMapper carMapper;

    @Override
    public void addNewCar(CarRequest carRequest) {
        Car car = new Car();
       // car.setCarStatus(carStatusRepository.findByName(ECarStatus.FREE));
        car.setCarType(carTypesRepository.findByName(ECarType.COMFORT));
        car.setBrand(carRequest.getBrand());
        car.setFuel(fuelTypesRepository.findByName(EFuel.PB95));
        car.setSeats(carRequest.getSeats());
        //car.setCarImage();
        car.setModel(carRequest.getModel());
        car.setTransmission(carRequest.getTransmission());
        carRepository.save(car);
    }

    @Override
    public void addNewCar(CarRequest carRequest, MultipartFile file) {
        Car car = new Car();
        car.setCarStatus(carStatusRepository.findByName(ECarStatus.FREE));
        car.setCarType(carTypesRepository.findByName(ECarType.COMFORT));
        car.setBrand(carRequest.getBrand());
        car.setFuel(fuelTypesRepository.findByName(EFuel.PB95));
        car.setSeats(carRequest.getSeats());


        //car.setCarImage(carImage);
        car.setModel(carRequest.getModel());
        car.setTransmission(carRequest.getTransmission());
        carRepository.save(car);
    }

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::carToResponse).collect(Collectors.toList());

    }
}

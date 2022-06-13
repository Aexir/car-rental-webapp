package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

import java.io.IOException;
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
        CarImage carImage = new CarImage();

        try {
            carImage.setName(carRequest.getBrand()+carRequest.getModel());
            //carImage.setName("XD");
            carImage.setContentType(file.getContentType());
            carImage.setData(file.getBytes());
            carImage.setSize(file.getSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        car.setCarImage(carImage);
        car.setModel(carRequest.getModel());
        car.setTransmission(carRequest.getTransmission());
        carRepository.save(car);
    }

    @Override
    public void addNewCar(String brand, String model, int seats, String transmission, String fuel, String carType, MultipartFile file) {
        Car car = new Car();
        car.setCarStatus(carStatusRepository.findByName(ECarStatus.FREE));
        car.setCarType(carTypesRepository.findByName(ECarType.COMFORT));
        car.setBrand(brand);
        car.setFuel(fuelTypesRepository.findByName(EFuel.PB95));
        car.setSeats(seats);
        CarImage carImage = new CarImage();

        try {
            carImage.setName(brand+model);
            //carImage.setName("XD");
            carImage.setContentType(file.getContentType());
            carImage.setData(file.getBytes());
            carImage.setSize(file.getSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        car.setCarImage(carImage);
        car.setModel(model);
        car.setTransmission(transmission);
        carRepository.save(car);
    }

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::carToResponse).collect(Collectors.toList());

    }
}

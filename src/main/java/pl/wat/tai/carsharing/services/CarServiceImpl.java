package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.entities.enums.ECarStatus;
import pl.wat.tai.carsharing.data.entities.enums.ECarType;
import pl.wat.tai.carsharing.data.entities.enums.EFuel;
import pl.wat.tai.carsharing.data.requests.CarStatusRequest;
import pl.wat.tai.carsharing.data.response.AboutCarResponse;
import pl.wat.tai.carsharing.data.response.CarResponse;
import pl.wat.tai.carsharing.mappers.CarImageMapper;
import pl.wat.tai.carsharing.mappers.CarMapper;
import pl.wat.tai.carsharing.repositories.*;
import pl.wat.tai.carsharing.services.interfaces.CarService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final CarImageMapper carImageMapper;

    @Override
    @Transactional
    public void addNewCar(String brand, String model, int seats, String transmission, String fuel, String carType, MultipartFile file, String engine, String plate, String vin, float price) {
        Car car = new Car();
        car.setCarStatus(carStatusRepository.findByName(ECarStatus.FREE));
        car.setCarType(carTypesRepository.findByName(ECarType.valueOf(carType.toUpperCase())));
        car.setBrand(brand);
        car.setFuel(fuelTypesRepository.findByName(EFuel.valueOf(fuel.toUpperCase())));
        car.setSeats(seats);
        car.setEngine(engine);
        car.setPlate(plate);
        car.setVin(vin);
        car.setPrice(price);
        if (file != null) {
            CarImage carImage = new CarImage();
            try {
                carImage.setName(brand + model);
                carImage.setContentType(file.getContentType());
                carImage.setData(file.getBytes());
                carImage.setSize(file.getSize());
                car.setCarImage(carImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            car.setCarImage(carImageRepository.findAll().get(0));
        }

        car.setModel(model);
        car.setTransmission(transmission);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::carToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AboutCarResponse aboutCar(long id) {
        return carMapper.carToAbout(carRepository.getReferenceById(id));
    }

    @Override
    public void setCarStatus(CarStatusRequest carStatusRequest) {
        Car car = carRepository.getReferenceById(carStatusRequest.getId());
        car.setCarStatus(carStatusRepository.findByName(ECarStatus.valueOf(carStatusRequest.getStatus())));
        carRepository.save(car);
    }

    @Override
    public void removeCar(long id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<AboutCarResponse> getUnique() {
        List<AboutCarResponse> aboutCarResponses = new ArrayList<>();

        List<Car> cars = carRepository.findAll();
        for (Car car : cars){
            AboutCarResponse aboutCarResponse = new AboutCarResponse();
            aboutCarResponse.setCarId(car.getId());
            aboutCarResponse.setBrand(car.getBrand());
            aboutCarResponse.setModel(car.getModel());
            aboutCarResponse.setEngine(car.getEngine());
            aboutCarResponse.setPrice(car.getPrice());
            boolean xd = false;
            for (AboutCarResponse aboutCarResponse1 : aboutCarResponses){
                if (aboutCarResponse1.equals(aboutCarResponse)){
                    xd = true;
                }
            }
            if (!xd) {

            }
        }

        for (AboutCarResponse aboutCarResponse: aboutCarResponses){
            aboutCarResponse.setUrl(carImageMapper.mapToFileResponse(carRepository.getReferenceById(aboutCarResponse.getCarId()).getCarImage()).getUrl());
        }

        return aboutCarResponses;
    }

    @Override
    @Transactional
    public void editCar(long id, String carStatus, MultipartFile file, String plate, Float price) {
        Car car = carRepository.getReferenceById(id);
        if (!carStatus.isEmpty()) car.setCarStatus(carStatusRepository.findByName(ECarStatus.valueOf(carStatus)));
        if (!plate.isEmpty()) car.setPlate(plate);
        if (!price.isNaN() && price > 0) car.setPrice(price);
        if (file != null) {
            CarImage carImage = new CarImage();
            try {
                carImage.setName(car.getBrand() + car.getModel());
                carImage.setContentType(file.getContentType());
                carImage.setData(file.getBytes());
                carImage.setSize(file.getSize());
            } catch (IOException e) {
                e.printStackTrace();
            }
            car.setCarImage(carImage);
        }
        carRepository.save(car);
    }
}

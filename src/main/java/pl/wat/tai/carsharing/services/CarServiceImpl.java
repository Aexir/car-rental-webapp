package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.CarResponse;
import pl.wat.tai.carsharing.mappers.CarMapper;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.services.interfaces.CarService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::carToCarRespone).toList();
    }

    @Override
    public List<CarResponse> getUniqueModels() {
        List<CarResponse> list = new ArrayList<>();
        list.add(new CarResponse(1, "Volkswagen", "Golf", "ON", 30, 30, false, 21, 21));
        list.add(new CarResponse(2, "Volkswagen", "Polo", "ON", 30, 30, false, 21, 21));
        list.add(new CarResponse(3, "Skoda", "Fabia", "ON", 30, 30, false, 21, 21));
        list.add(new CarResponse(4, "Skoda", "Octavia", "ON", 30, 30, false, 21, 21));
        list.add(new CarResponse(5, "BMW", "E4Smiec", "ON", 30, 30, false, 21, 21));
        list.add(new CarResponse(6, "Nissan", "GTR", "ON", 30, 30, false, 21, 21));

        return list;
    }
}

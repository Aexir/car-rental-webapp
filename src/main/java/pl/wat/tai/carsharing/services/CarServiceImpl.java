package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.CarRespone;
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
    public List<CarRespone> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::carToCarRespone).toList();
    }

    @Override
    public List<CarRespone> getUniqueModels() {
        List<CarRespone> list = new ArrayList<>();
        list.add(new CarRespone(1, "Volkswagen", "Golf", "ON", 30, 30,false, 21, 21));
        list.add(new CarRespone(2, "Volkswagen", "Polo", "ON", 30, 30,false, 21, 21));
        list.add(new CarRespone(3, "Skoda", "Fabia", "ON", 30, 30,false, 21, 21));
        list.add(new CarRespone(4, "Skoda", "Octavia", "ON", 30, 30,false, 21, 21));
        list.add(new CarRespone(5, "BMW", "E4Smiec", "ON", 30, 30,false, 21, 21));
        list.add(new CarRespone(6, "Nissan", "GTR", "ON", 30, 30,false, 21, 21));

        return list;
    }
}

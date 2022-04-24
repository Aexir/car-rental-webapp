package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.CarRespone;
import pl.wat.tai.carsharing.mappers.CarMapper;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.services.interfaces.CarService;

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
}

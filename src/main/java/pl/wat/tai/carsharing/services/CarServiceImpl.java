package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.requests.CarRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;
import pl.wat.tai.carsharing.services.interfaces.CarService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    @Override
    public void addNewCar(CarRequest carRequest) {

    }

    @Override
    public List<CarResponse> getAllCars() {
        return null;
    }
}

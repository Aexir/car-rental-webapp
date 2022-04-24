package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.CarRespone;
import pl.wat.tai.carsharing.data.Location;
import pl.wat.tai.carsharing.services.interfaces.CarService;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/all")
    public List<CarRespone> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/location/{id}")
    public Location getCarLocation(@RequestAttribute int id){
        return new Location(id, 1,1);
    }
}

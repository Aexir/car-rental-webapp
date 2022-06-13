package pl.wat.tai.carsharing.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.requests.CarRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;
import pl.wat.tai.carsharing.services.interfaces.CarService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cars")

public class CarController {

    private final CarService carService;

    @PostMapping("/add")
    public void addNewCar(@RequestParam   String brand, @RequestParam String model,
                          @RequestParam  int seats,
                          @RequestParam   String transmission,
                          @RequestParam  String fuel,
                          @RequestParam   String carType,@RequestParam(name = "file", required = false) MultipartFile file){
//        carService.addNewCar(carRequest, file);
        carService.addNewCar(brand, model, seats, transmission, fuel, carType, file);
    }


    @GetMapping("/all")
    public List<CarResponse> getAllCars(){
        return carService.getAllCars();
    }
}

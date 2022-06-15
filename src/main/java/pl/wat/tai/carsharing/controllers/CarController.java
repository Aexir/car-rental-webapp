package pl.wat.tai.carsharing.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.requests.CarStatusRequest;
import pl.wat.tai.carsharing.data.response.AboutCarResponse;
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
                          @RequestParam   String carType,@RequestParam(name = "file", required = false) MultipartFile file,
                          @RequestParam String engine, @RequestParam String plate, @RequestParam String vin, @RequestParam float price){
//        carService.addNewCar(carRequest, file);
        carService.addNewCar(brand, model, seats, transmission, fuel, carType, file, engine, plate, vin, price);
    }

    @GetMapping("")
    public AboutCarResponse aboutCar(@RequestParam long id){
        return carService.aboutCar(id);
    }

    @GetMapping("/all")
    public List<CarResponse> getAllCars(){
        return carService.getAllCars();
    }

    @PostMapping
    public void setCarStatus(@RequestBody CarStatusRequest carStatusRequest){
        carService.setCarStatus(carStatusRequest);
    }

    @PostMapping("/edit")
    public void editCar(@RequestParam long id,
                        @RequestParam String carStatus,
                        @RequestParam MultipartFile file,
                        @RequestParam String plate,
                        @RequestParam Float priceRequestBody){
        carService.editCar(id, carStatus, file, plate, priceRequestBody);
    }

    @PostMapping("/remove/{id}")
    public void removeCar(@PathVariable long id){
        carService.removeCar(id);
    }
}

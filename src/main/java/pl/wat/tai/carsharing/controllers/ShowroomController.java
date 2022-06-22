package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.CarResponse;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;
import pl.wat.tai.carsharing.services.interfaces.ShowroomService;

import java.util.List;

@RestController
@RequestMapping("/showroom")
@RequiredArgsConstructor
@CrossOrigin
public class ShowroomController {


    private final ShowroomService showroomService;

    @PostMapping("/add")
    public void addShowroom(@RequestBody ShowroomRequest showroomRequest) {
        showroomService.addShowroom(showroomRequest);
    }

    @DeleteMapping("/remove/{name}")
    public void removeShowroom(@PathVariable String name) {
        if (!showroomService.get(name).getCarList().isEmpty()){
            for (CarResponse car : showroomService.get(name).getCarList()){
                showroomService.removeCarFromShowroom(name, car.getId());
            }
        }
        showroomService.removeShowroom(name);
    }

    @DeleteMapping("/remove/{name}/{id}")
    public void removeCarFromShowroom(@PathVariable String name, @PathVariable long id) {
        showroomService.removeCarFromShowroom(name, id);
    }


    @GetMapping("/all")
    public List<ShowroomResponse> getAll() {
        return showroomService.getAll();
    }

    @GetMapping("/{name}")
    public ShowroomResponse get(@PathVariable String name) {
        return showroomService.get(name);
    }

    @PostMapping("/{name}/{id}")
    public void addCarToShowroom(@PathVariable String name, @PathVariable long id) {
        showroomService.removeCarFromShowroom(id);
        showroomService.addCarToShowroom(name, id);
    }

    @GetMapping("/names")
    public List<String> getShowroomNames() {
        return showroomService.getShowroomNames();
    }

}

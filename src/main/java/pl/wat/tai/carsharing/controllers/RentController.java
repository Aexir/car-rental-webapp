package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.requests.EditRentRequest;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.RentResponse;
import pl.wat.tai.carsharing.services.interfaces.RentService;

import java.util.List;

@CrossOrigin
@RequestMapping("/rent")
@RequiredArgsConstructor
@RestController
public class RentController {

    private final RentService rentService;

    @GetMapping("/all")
    public List<RentResponse> getAllRentals() {
        return rentService.getAllRentals();
    }

    @GetMapping("/active")
    public List<RentResponse> getActiveRentals() {
        return rentService.getActiveRentals();
    }

    @PostMapping("/create")
    public void createRent(@RequestBody RentRequest rentRequest) {
        rentService.createRent(rentRequest);
    }

    @GetMapping("{id}")
    public List<RentResponse> getClientRentals(@PathVariable long id) {
        return rentService.getClientRentals(id);
    }

    @GetMapping("/showroom/{id}")
    public List<RentResponse> getShowroomRentals(@PathVariable long id) {
        return rentService.getShowroomRentals(id);
    }

    @PostMapping("/edit")
    public void editRent(@RequestBody EditRentRequest editRentRequest) {
        rentService.editRent(editRentRequest);
    }
}

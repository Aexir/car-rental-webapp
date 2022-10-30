package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.RentResponse;
import pl.wat.tai.carsharing.services.interfaces.RentService;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/rent")
@RequiredArgsConstructor
@RestController
public class RentController {

    private final RentService rentService;

    @GetMapping("/all")
    //@PreAuthorize("hasRole('ADMIN')")
    public List<RentResponse> getAllRentals() {
        return rentService.getAllRentals();
    }

    @GetMapping("/active")
    //@PreAuthorize("hasRole('ADMIN')")
    public List<RentResponse> getActiveRentals() {
        return rentService.getActiveRentals();
    }

    @PostMapping("/create")
    //@PreAuthorize("hasRole('ACTIVE')")
    public void createRent(@RequestBody RentRequest rentRequest) {
        rentService.createRent(rentRequest);
    }

    @GetMapping("{id}")
    //@PreAuthorize("hasRole('ACTIVE')")
    public List<RentResponse> getClientRentals(@PathVariable long id) {
        return rentService.getClientRentals(id);
    }

    @GetMapping("/showroom/{id}")
    //@PreAuthorize("hasRole('ACTIVE')")
    public List<RentResponse> getShowroomRentals(@PathVariable long id) {
        return rentService.getShowroomRentals(id);
    }

    @PutMapping("/edit")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editRent(@RequestParam long id) {
        return rentService.editRent(id);
    }
}

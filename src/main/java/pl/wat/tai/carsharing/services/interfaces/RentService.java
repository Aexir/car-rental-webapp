package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.wat.tai.carsharing.data.requests.EditRentRequest;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.RentResponse;

import java.util.List;

public interface RentService {
    List<RentResponse> getAllRentals();

    List<RentResponse> getActiveRentals();

    void createRent(RentRequest rentRequest);

    List<RentResponse> getClientRentals(long id);

    List<RentResponse> getShowroomRentals(long id);

    ResponseEntity<?> editRent(long rentId);
}

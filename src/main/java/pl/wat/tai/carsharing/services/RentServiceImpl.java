package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.requests.EditRentRequest;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.MessageResponse;
import pl.wat.tai.carsharing.data.response.RentResponse;
import pl.wat.tai.carsharing.mappers.RentMapper;
import pl.wat.tai.carsharing.repositories.RentRepository;
import pl.wat.tai.carsharing.services.interfaces.RentService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final RentMapper rentMapper;

    @Override
    @Transactional
    public List<RentResponse> getAllRentals() {
        return rentRepository.findAll().stream()
                .map(rentMapper::rentToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RentResponse> getActiveRentals() {
        return rentRepository.findAll().stream()
                .filter(x -> x.getRentStatus().equals("ACTIVE"))
                .map(rentMapper::rentToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createRent(RentRequest rentRequest) {
        rentRepository.save(rentMapper.requestToRent(rentRequest));
    }

    @Override
    @Transactional
    public List<RentResponse> getClientRentals(long id) {
        return rentRepository.findAll().stream()
                .filter(x -> x.getUser().getId() == id)
                .map(rentMapper::rentToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RentResponse> getShowroomRentals(long id) {
        return rentRepository.findAll().stream()
                .filter(x -> x.getShowroom().getId() == id)
                .map(rentMapper::rentToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<?> editRent(long rentId) {
        if (rentMapper.editRequestToRent(rentId)== null){
            return ResponseEntity.badRequest().body(new MessageResponse("FAILED TO COS TAM"));
        }
        rentRepository.save(rentMapper.editRequestToRent(rentId));
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("OK"));
    }
}

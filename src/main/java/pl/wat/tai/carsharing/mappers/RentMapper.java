package pl.wat.tai.carsharing.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.Rent;
import pl.wat.tai.carsharing.data.requests.EditRentRequest;
import pl.wat.tai.carsharing.data.requests.RentRequest;
import pl.wat.tai.carsharing.data.response.RentResponse;
import pl.wat.tai.carsharing.repositories.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RentMapper {

    private final ShowroomRepository showroomRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final RentRepository rentRepository;

    public RentResponse rentToResponse(Rent rent) {
        RentResponse rentResponse = new RentResponse();

        rentResponse.setId(rent.getId());
        rentResponse.setUserId(rent.getUser().getId());
        rentResponse.setShowroomName(rent.getShowroom().getName());
        rentResponse.setCarId(rent.getCar().getId());
        rentResponse.setStartDate(rent.getStartDate());
        rentResponse.setEndDate(rent.getEndDate());
        rentResponse.setPrice(rent.getPrice());
        rentResponse.setRentStatus(rent.getRentStatus());
        /*
         private long id;
    private User user;
    private Showroom showroom;
    private Car car;
    private Date startDate;
    private Date endDate;
    private String rentStatus;
    private float price;*/

        return rentResponse;
    }

    public Rent requestToRent(RentRequest rentRequest) {
        Rent rent = new Rent();

        /*    private long userId;
    private String showroomName;
    private long  carId;
    private Date startDate;
    private Date endDate;*/

        rent.setShowroom(showroomRepository.findByName(rentRequest.getShowroomName()));
        Car car = carRepository.getReferenceById(rentRequest.getCarId());

        if (rentRequest.getEndDate().before(rentRequest.getStartDate())) {
            throw new RuntimeException("END DATE BEFORE START DATE");
        } else {
            rent.setStartDate(rentRequest.getStartDate());
            rent.setEndDate(rentRequest.getEndDate());
        }
        rent.setRentStatus("ACTIVE");

        long diff = Math.abs(rent.getEndDate().getTime() - rent.getStartDate().getTime());
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        float price = carRepository.getReferenceById(rentRequest.getCarId()).getPrice();
        rent.setPrice(days * price);
        rent.setUser(userRepository.getReferenceById(rentRequest.getUserId()));
        car.setCarStatus("TAKEN");
        rent.setCar(car);
        carRepository.save(car);

        return rent;
    }

    public Rent editRequestToRent(long rentId) {
        Rent rent = rentRepository.getReferenceById(rentId);
        if (Objects.equals(rent.getRentStatus(), "ACTIVE")){
            rent.setRentStatus("ENDED");
        }
        return rent;
    }


}

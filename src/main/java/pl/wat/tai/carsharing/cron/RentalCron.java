package pl.wat.tai.carsharing.cron;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.Rent;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.repositories.RentRepository;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class RentalCron {

    private static final Logger log = LoggerFactory.getLogger(RentalCron.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final RentRepository rentRepository;
    private final CarRepository carRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void endRentals() {
        Date date = new Date(System.currentTimeMillis());
        for (Rent rent : rentRepository.findAll()) {
            Car car = rent.getCar();
            if (date.after(rent.getEndDate())) {
                rent.setRentStatus("ENDED");
                car.setCarStatus("FREE");
                carRepository.save(car);
                rentRepository.save(rent);
            }
        }
    }

}
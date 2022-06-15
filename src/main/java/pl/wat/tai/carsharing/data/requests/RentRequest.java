package pl.wat.tai.carsharing.data.requests;

import lombok.Data;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.entities.User;

import java.sql.Date;

@Data
public class RentRequest {
    private long userId;
    private String showroomName;
    private long  carId;
    private Date startDate;
    private Date endDate;
}

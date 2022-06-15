package pl.wat.tai.carsharing.data.response;

import lombok.Data;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.entities.User;

import java.sql.Date;

@Data
public class RentResponse {
    private long id;
    private long userId;
    private String showroomName;
    private long carId;
    private Date startDate;
    private Date endDate;
    private String rentStatus;
    private float price;
}

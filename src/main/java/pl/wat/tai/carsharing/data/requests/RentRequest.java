package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@NotBlank
public class RentRequest {
    private long userId;
    private String showroomName;
    private long carId;
    private Date startDate;
    private Date endDate;
}

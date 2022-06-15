package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import java.sql.Date;

@Data
public class EditRentRequest {
    private long rentId;
    private Date startDate;
    private Date endDate;
    private float price;
    private String rentStatus;
}

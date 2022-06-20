package pl.wat.tai.carsharing.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    private String orderId;
    private String rentId;
    private Date orderCreateDate;
    private String customerIp;
    private String description;
    private String currency;
    private int totalAmount;


}

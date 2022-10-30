package pl.wat.tai.carsharing.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Showroom showroom;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    private Showroom endShowroom;
    private Date startDate;
    private Date endDate;

    private String rentStatus;
    private float price;


}

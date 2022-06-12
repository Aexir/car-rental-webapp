package pl.wat.tai.carsharing.data.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Showroom showroom;
    @ManyToOne
    private Car car;

    private Date startDate;
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "rent_status",
            joinColumns = @JoinColumn(name = "rent_id"),
            inverseJoinColumns = @JoinColumn(name = "rent_status_id"))
    private RentStatus rentStatus;


}

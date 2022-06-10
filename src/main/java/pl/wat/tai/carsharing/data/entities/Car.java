package pl.wat.tai.carsharing.data.entities;

import javax.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "car_fuel",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "fuel_id"))
    private Fuel fuel;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinTable(name = "car_type",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private CarType carType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "car_car_image",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private CarImage carImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "car_carStatus",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "car_status_id"))
    private CarStatus carStatus;
}

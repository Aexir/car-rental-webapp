package pl.wat.tai.carsharing.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;//
    private String model;//
    private int seats;//
    private String transmission;//
    private String engine;
    private float price;
    private String plate;
    private String vin;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "car_car_image",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private CarImage carImage;
    private String carStatus;
}

package pl.wat.tai.carsharing.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private Producers producer;
    private String model;
    private String fuelType;
    private float fuelTank;
    private float fuelTankSize;
    private boolean inUse;
    private long latitude;
    private long altitude;
    @OneToMany
    private Set<Rent> rentals = new HashSet<>();
}

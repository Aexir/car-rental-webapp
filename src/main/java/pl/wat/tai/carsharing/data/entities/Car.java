package pl.wat.tai.carsharing.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}

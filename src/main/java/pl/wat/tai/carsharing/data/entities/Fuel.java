package pl.wat.tai.carsharing.data.entities;

import pl.wat.tai.carsharing.data.entities.enums.EFuel;
import pl.wat.tai.carsharing.data.entities.enums.ERole;

import javax.persistence.*;

@Entity
@Table(name = "fuels")

public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EFuel name;
}

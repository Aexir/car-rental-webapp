package pl.wat.tai.carsharing.data.entities;

import lombok.Getter;
import pl.wat.tai.carsharing.data.entities.enums.EFuel;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "fuels")

public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EFuel name;
}

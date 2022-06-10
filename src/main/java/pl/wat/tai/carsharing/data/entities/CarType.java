package pl.wat.tai.carsharing.data.entities;

import pl.wat.tai.carsharing.data.entities.enums.ECarType;

import javax.persistence.*;

@Entity
@Table(name = "cartypes")
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ECarType name;
}

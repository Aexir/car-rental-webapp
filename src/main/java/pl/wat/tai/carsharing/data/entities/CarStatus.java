package pl.wat.tai.carsharing.data.entities;

import pl.wat.tai.carsharing.data.entities.enums.ECarType;

import javax.persistence.*;

@Entity
public class CarStatus {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Enumerated(EnumType.STRING)
        @Column(length = 20)
        private ECarType name;
    }

package pl.wat.tai.carsharing.data.entities;

import lombok.Getter;
import pl.wat.tai.carsharing.data.entities.enums.ECarStatus;
import pl.wat.tai.carsharing.data.entities.enums.ECarType;

import javax.persistence.*;

@Entity
@Getter
public class CarStatus {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Enumerated(EnumType.STRING)
        @Column(length = 20)
        private ECarStatus name;
    }

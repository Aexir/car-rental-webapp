package pl.wat.tai.carsharing.data.entities;

import lombok.Getter;
import lombok.Setter;
import pl.wat.tai.carsharing.data.entities.enums.ERentStatus;
import pl.wat.tai.carsharing.data.entities.enums.ERole;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rentstatuses")
public class RentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERentStatus name;
}

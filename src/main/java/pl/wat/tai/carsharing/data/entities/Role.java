package pl.wat.tai.carsharing.data.entities;

import lombok.Getter;
import lombok.Setter;
import pl.wat.tai.carsharing.data.entities.enums.ERole;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
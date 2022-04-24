package pl.wat.tai.carsharing.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    @OneToOne(fetch=FetchType.LAZY)
    @MapsId
    private Address address;
}

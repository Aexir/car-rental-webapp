package pl.wat.tai.carsharing.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Date birthDate;
    @OneToOne(fetch=FetchType.LAZY)
    @MapsId
    private Address address;
    @OneToMany
    private Set<Rent> rentals = new HashSet<>();
}

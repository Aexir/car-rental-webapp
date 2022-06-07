package pl.wat.tai.carsharing.data.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String zipcode;
    private String city;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;
}

package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wat.tai.carsharing.data.entities.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {
}

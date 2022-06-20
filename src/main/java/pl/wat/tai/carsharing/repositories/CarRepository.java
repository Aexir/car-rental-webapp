package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wat.tai.carsharing.data.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
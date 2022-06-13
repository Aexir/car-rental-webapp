package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wat.tai.carsharing.data.entities.CarStatus;
import pl.wat.tai.carsharing.data.entities.Role;
import pl.wat.tai.carsharing.data.entities.enums.ECarStatus;
import pl.wat.tai.carsharing.data.entities.enums.ERole;

import java.util.Optional;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Integer> {
    CarStatus findByName(ECarStatus name);

}

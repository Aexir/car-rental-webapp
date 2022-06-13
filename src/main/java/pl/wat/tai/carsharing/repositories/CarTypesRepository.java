package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wat.tai.carsharing.data.entities.CarStatus;
import pl.wat.tai.carsharing.data.entities.CarType;
import pl.wat.tai.carsharing.data.entities.enums.ECarStatus;
import pl.wat.tai.carsharing.data.entities.enums.ECarType;

@Repository
public interface CarTypesRepository extends JpaRepository<CarType, Integer> {
    CarType findByName(ECarType name);

}

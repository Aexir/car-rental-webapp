package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wat.tai.carsharing.data.entities.Fuel;
import pl.wat.tai.carsharing.data.entities.enums.EFuel;

@Repository
public interface FuelTypesRepository extends JpaRepository<Fuel, Integer> {
    Fuel findByName(EFuel pb95);
}

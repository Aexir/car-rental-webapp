package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wat.tai.carsharing.data.entities.CarImage;
import org.springframework.stereotype.Repository;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, String> {
    CarImage getByName(String name);
}

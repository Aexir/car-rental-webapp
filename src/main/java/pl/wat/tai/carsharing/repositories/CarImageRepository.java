package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wat.tai.carsharing.data.entities.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, String> {
}

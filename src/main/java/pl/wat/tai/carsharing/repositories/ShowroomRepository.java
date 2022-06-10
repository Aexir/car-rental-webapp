package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wat.tai.carsharing.data.entities.Showroom;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Long> {

    @Query("FROM Showroom t WHERE t.name = ?1")
    Showroom findByName(String name);
}

package pl.wat.tai.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wat.tai.carsharing.data.entities.VerificationToken;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
}

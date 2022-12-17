package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.TripStatus;

@Repository
public interface TripStatusRepository extends JpaRepository<TripStatus, Long> {
}
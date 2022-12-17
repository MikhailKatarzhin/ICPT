package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.TripStatus;

public interface TripStatusRepository extends JpaRepository<TripStatus, Long> {
}
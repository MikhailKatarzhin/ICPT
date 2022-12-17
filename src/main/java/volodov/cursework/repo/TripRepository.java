package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
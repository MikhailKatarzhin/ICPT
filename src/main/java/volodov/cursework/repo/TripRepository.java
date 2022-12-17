package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
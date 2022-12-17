package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.RouteSequence;

public interface RouteSequenceRepository extends JpaRepository<RouteSequence, Long> {
}
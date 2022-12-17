package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.RouteSequence;

@Repository
public interface RouteSequenceRepository extends JpaRepository<RouteSequence, Long> {
}
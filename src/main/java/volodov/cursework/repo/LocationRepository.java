package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
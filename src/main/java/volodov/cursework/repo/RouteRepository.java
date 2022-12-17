package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
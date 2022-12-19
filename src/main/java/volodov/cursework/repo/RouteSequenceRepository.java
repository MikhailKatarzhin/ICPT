package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Location;
import volodov.cursework.model.RouteSequence;

import java.util.List;

@Repository
public interface RouteSequenceRepository extends JpaRepository<RouteSequence, Long> {

    @Query(
            value = "select * from route_sequence" +
                    " where route_sequence.trip_id = ?1" +
                    " ORDER BY arrival_time",
            nativeQuery = true
    )
    List<RouteSequence> getByTripId(Long tripId);

    @Query(
            value = "select count(*) from route_sequence" +
                    " where id = ?1",
            nativeQuery = true
    )
    Long countById(Long routeSequenceId);
}
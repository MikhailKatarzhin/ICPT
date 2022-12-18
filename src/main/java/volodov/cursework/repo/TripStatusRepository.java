package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Location;
import volodov.cursework.model.TripStatus;

import java.util.List;

@Repository
public interface TripStatusRepository extends JpaRepository<TripStatus, Long> {

    TripStatus getById(Long tripStatusId);
    @Query(
            value = "select * from trip_status" +
                    " where trip_status.id > ?1",
            nativeQuery = true
    )
    List<TripStatus> getAllGreaterThenId(Long statusId);
}
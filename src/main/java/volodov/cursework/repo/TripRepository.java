package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Trip;

import java.time.Instant;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query(
            value = "select COUNT(*) from trip where driver_id = ?1",
            nativeQuery = true
    )
    Long countByDriverId(Long driverId);

    @Query(
            value = "SELECT COUNT(*) FROM trip" +
                    " WHERE departure_time >= ?1 AND departure_time <= ?2",
            nativeQuery = true
    )
    Long countByDepartureTimeInterval(Instant departureTimeMin, Instant departureTimeMax);

    @Query(
            value = "select COUNT(*) from trip " +
                    "where driver_id = ?1 and status_id = ?2",
            nativeQuery = true
    )
    Long countByDriverIdAndStatusId(Long driverId, Long statusId);

    @Query(
            value = "SELECT COUNT(*) FROM trip" +
                    " WHERE departure_time >= ?1 AND departure_time <= ?2" +
                    " AND status_id = ?3",
            nativeQuery = true
    )
    Long countByDepartureTimeIntervalAndStatusId(Instant departureTimeMin, Instant departureTimeMax, Long statusId);

    @Query(
            value = "select * from trip where driver_id = ?1" +
                    " order by departure_time desc" +
                    " limit ?2 offset ?3",
            nativeQuery = true
    )
    List<Trip> findByDriverIdAndLimitOffset(Long driverId, Long limit, Long offset);

    @Query(
            value = "SELECT * FROM trip" +
                    " WHERE departure_time >= ?1 AND departure_time <= ?2" +
                    " order by departure_time desc " +
                    " limit ?3 offset ?4",
            nativeQuery = true
    )
    List<Trip> findByDepartureTimeIntervalAndLimitOffset(Instant departureTimeMin, Instant departureTimeMax, Long limit, Long offset);

    @Query(
            value = "select * from trip where driver_id = ?1" +
                    " and status_id = ?2" +
                    " order by departure_time desc " +
                    " limit ?3 offset ?4",
            nativeQuery = true
    )
    List<Trip> findByDriverIdAndStatusIdAndLimitOffset(Long driverId, Long statusId, Long limit, Long offset);

    @Query(
            value = "SELECT * FROM trip" +
                    " WHERE departure_time >= ?1 AND departure_time <= ?2" +
                    " AND status_id = ?3" +
                    " order by departure_time desc " +
                    " limit ?4 offset ?5",
            nativeQuery = true
    )
    List<Trip> findByDepartureTimeIntervalAndStatusIdAndLimitOffset(Instant departureTimeMin, Instant departureTimeMax, Long statusId, Long limit, Long offset);
}
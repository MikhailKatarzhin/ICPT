package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Bill;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(
            value = "select COUNT(*) from bill where id=?1",
            nativeQuery = true
    )
    Long countById(Long id);

    @Query(
            value = "select COUNT(*) from bill where consumer_id=?1",
            nativeQuery = true
    )
    Long countByConsumerId(Long consumerId);

    @Query(
            value = "select COUNT(*) from bill" +
                    " where consumer_id=?1 and trip_id=?2",
            nativeQuery = true
    )
    Long countByConsumerIdAndTripId(Long consumerId, Long tripId);

    @Query(
            value = "select * from bill where consumer_id=?1",
            nativeQuery = true
    )
    List<Bill> findByConsumerId(Long consumerId);

    @Query(
            value = "select * from bill where consumer_id=?1",
            nativeQuery = true
    )
    List<Bill> findByConsumerIdAndLimitOffset(Long consumerId, long limit, long offset);

    @Query(
            value = "select * from bill" +
                    " inner join trip t on bill.trip_id = t.id" +
                    " where consumer_id=?1" +
                    " order by t.departure_time desc",
            nativeQuery = true
    )
    List<Bill> findByConsumerIdOrderByDepartureTime(Long consumerId);
}
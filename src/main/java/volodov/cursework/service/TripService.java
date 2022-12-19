package volodov.cursework.service;

import org.springframework.stereotype.Service;
import volodov.cursework.model.Trip;
import volodov.cursework.repo.TripRepository;

import java.util.LinkedList;
import java.util.List;

import static volodov.cursework.config.ProjectConstants.*;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserService userService;

    public TripService(TripRepository tripRepository, UserService userService) {
        this.tripRepository = tripRepository;
        this.userService = userService;
    }

    public Trip getById(Long tripId){
        return tripRepository.getById(tripId);
    }

    public Long countByRemoteDriver(){
        return countByDriverId(userService.getRemoteUserId());
    }

    public Long countByDriverId(Long driverId){
        return tripRepository.countByDriverId(driverId);
    }

    public List<Trip> findByRemoteDriverAndLimitOffset(Long currentPage){
        if (countByRemoteDriver() == 0)
            return new LinkedList<>();
        return tripRepository.findByDriverIdAndLimitOffset(userService.getRemoteUserId(), TRIP_ROW_COUNT, (currentPage - 1) * TRIP_ROW_COUNT);
    }

    public Long pageCountByRemoteDriver(){
        long nPage = countByRemoteDriver() / TRIP_ROW_COUNT + (countByRemoteDriver() % TRIP_ROW_COUNT == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    public Long countByNowAndStatusId(Long statusId){
        return tripRepository.countByNowAndStatusId(statusId);
    }

    public Long pageCountByNowAndStatus(Long statusId){
        long count = countByNowAndStatusId(statusId);
        long nPage = count / TRIP_CONSUMER_ROW_COUNT + (count % TRIP_CONSUMER_ROW_COUNT == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    public Long pageCountAvailable(){
        return pageCountByNowAndStatus(2L);
    }

    public List<Trip> findAvailableTripsByLimitOffset(Long currentPage){
        return findByByNowAndStatusAndLimitOffset( 2L, currentPage);
    }

    public List<Trip> findByByNowAndStatusAndLimitOffset(Long statusId, Long currentPage){
        if (countByNowAndStatusId(statusId) == 0)
            return new LinkedList<>();
        return tripRepository.findByNowAndStatusIdAndLimitOffset
                (statusId, TRIP_CONSUMER_ROW_COUNT, (currentPage - 1) * TRIP_CONSUMER_ROW_COUNT);
    }

    public Trip save(Trip trip){
        return tripRepository.save(trip);
    }
}

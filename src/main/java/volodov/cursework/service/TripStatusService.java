package volodov.cursework.service;

import org.springframework.stereotype.Service;
import volodov.cursework.model.TripStatus;
import volodov.cursework.repo.TripStatusRepository;

@Service
public class TripStatusService {
    private final TripStatusRepository tripStatusRepository;

    public TripStatusService(TripStatusRepository tripStatusRepository) {
        this.tripStatusRepository = tripStatusRepository;
    }

    public TripStatus getById(Long statusId){
        return tripStatusRepository.getById(statusId);
    }
}

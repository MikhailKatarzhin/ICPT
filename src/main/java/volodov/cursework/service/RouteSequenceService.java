package volodov.cursework.service;

import org.springframework.stereotype.Service;
import volodov.cursework.model.RouteSequence;
import volodov.cursework.repo.RouteSequenceRepository;

import java.util.List;

@Service
public class RouteSequenceService {

    private final RouteSequenceRepository routeSequenceRepository;

    public RouteSequenceService(RouteSequenceRepository routeSequenceRepository) {
        this.routeSequenceRepository = routeSequenceRepository;
    }

    public RouteSequence save(RouteSequence routeSequence){
        return routeSequenceRepository.save(routeSequence);
    }

    public void delete(Long routeSequenceId){
        if (countById(routeSequenceId) == 0)
            return;
        RouteSequence routeSequence = routeSequenceRepository.getById(routeSequenceId);
        routeSequenceRepository.delete(routeSequence);
    }

    public Long countById(Long routeSequenceId){
        return routeSequenceRepository.countById(routeSequenceId);
    }

    public List<RouteSequence> getByTripId(Long tripId){
        return routeSequenceRepository.getByTripId(tripId);
    }
}

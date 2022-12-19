package volodov.cursework.service;

import org.springframework.stereotype.Service;
import volodov.cursework.model.Location;
import volodov.cursework.repo.LocationRepository;

import java.util.LinkedList;
import java.util.List;

import static volodov.cursework.config.ProjectConstants.LOCATION_ROW_COUNT;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Long countByLocationName(String locationName){
        return locationRepository.countByName(locationName);
    }

    public Long countLikeLocationName(String locationName){
        return locationRepository.countLikeName('%'+locationName+'%');
    }

    public Location findByLocationName(String locationName){
        if (countByLocationName(locationName) == 0)
            return null;
        return locationRepository.getByName(locationName);
    }

    public List<Location> findLikeLocationNameByNumberPage(String locationName, long currentPage){
        if (countLikeLocationName(locationName) == 0)
            return new LinkedList<>();
        return locationRepository.getLikeNameByLimitOffset('%'+locationName+'%', LOCATION_ROW_COUNT, (currentPage - 1) * LOCATION_ROW_COUNT);
    }

    public List<Location> findByNumberPage(long currentPage){
        if (locationRepository.count() == 0)
            return new LinkedList<>();
        return locationRepository.getLikeNameByLimitOffset("%", LOCATION_ROW_COUNT, (currentPage - 1) * LOCATION_ROW_COUNT);
    }

    public List<Location> findLikeLocationName(String locationName){
        if (countLikeLocationName(locationName) == 0)
            return new LinkedList<>();
        return locationRepository.getLikeName("%"+locationName+"%");
    }

    public Location findOneLikeLocationName(String locationName){
        if (countLikeLocationName(locationName) == 0)
            return null;
        return findLikeLocationName(locationName).get(0);
    }

    public Long pageCount(){
        long nPage = locationRepository.count() / LOCATION_ROW_COUNT + (locationRepository.count() % LOCATION_ROW_COUNT == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    public Long pageCountLikeName(String locationName){
        long nPage = countLikeLocationName(locationName) / LOCATION_ROW_COUNT + (countLikeLocationName(locationName) % LOCATION_ROW_COUNT == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    public Location save(Location location){
        return locationRepository.save(location);
    }
}

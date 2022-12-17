package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import volodov.cursework.model.Personality;

public interface PersonalityRepository extends JpaRepository<Personality, String> {

    @Query(
            value = "select * from personality where series_and_number = ?",
            nativeQuery = true
    )
    Personality  getBySeriesAndNumber(String seriesAndNumber);

    @Query(
            value = "select COUNT(*) from personality where series_and_number = ?",
            nativeQuery = true
    )
    Long  countBySeriesAndNumber(String seriesAndNumber);
}
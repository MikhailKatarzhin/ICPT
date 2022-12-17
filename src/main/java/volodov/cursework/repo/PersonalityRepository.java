package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.Personality;

public interface PersonalityRepository extends JpaRepository<Personality, String> {
}
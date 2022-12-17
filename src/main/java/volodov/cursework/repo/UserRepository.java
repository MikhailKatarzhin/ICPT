package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
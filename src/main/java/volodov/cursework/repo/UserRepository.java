package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import volodov.cursework.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(
            value = "select count(*) from \"user\" where email = ?",
            nativeQuery = true
    )
    Long countByEmail(String email);
}
package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import volodov.cursework.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(
            value = "select * from role where id = ?",
            nativeQuery = true
    )
    Role getById(Long id);
}
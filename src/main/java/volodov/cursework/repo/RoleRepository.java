package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import volodov.cursework.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(
            value = "select count(*) from \"user\" where email = ?",
            nativeQuery = true
    )
    Long countByEmail(String email);

    @Query(
            value = "select count(*) from \"user\" where role_id = ?",
            nativeQuery = true
    )
    Long countByRoleId(Long roleId);

    @Query(
            value = "SELECT * FROM \"user\""
                    + " WHERE role_id = ?1"
                    + " ORDER BY username"
                    + " LIMIT ?2 OFFSET ?3"
            , nativeQuery = true
    )
    List<User> selectByRoleIdAndLimitOffset(Long roleId, Long limit, Long offset);
}
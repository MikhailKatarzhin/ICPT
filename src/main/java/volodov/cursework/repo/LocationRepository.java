package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Location;
import volodov.cursework.model.Role;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(
            value = "select * from location where id = ?",
            nativeQuery = true
    )
    Location getById(Long id);

    @Query(
            value = "select * from location where name = ?",
            nativeQuery = true
    )
    Location getByName(String name);

    @Query(
            value = "select * from location" +
                    " where name ILIKE ?1" +
                    " ORDER BY name" +
                    " LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<Location> getLikeNameByLimitOffset(String name, Long limit, Long offset);

    @Query(
            value = "select * from location where name ILIKE ?1" +
                    " ORDER BY name",
            nativeQuery = true
    )
    List<Location> getLikeName(String name);

    @Query(
            value = "select * from location where name ILIKE ?1" +
                    " ORDER BY name",
            nativeQuery = true
    )
    Location getOneLikeName(String name);

    @Query(
            value = "select COUNT(*) from location where name = ?",
            nativeQuery = true
    )
    Long countByName(String name);

    @Query(
            value = "select count(*) from location where name ILIKE ?",
            nativeQuery = true
    )
    Long countLikeName(String name);
}
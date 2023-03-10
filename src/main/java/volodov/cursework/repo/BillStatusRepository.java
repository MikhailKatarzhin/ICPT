package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.BillStatus;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatus, Long> {
}
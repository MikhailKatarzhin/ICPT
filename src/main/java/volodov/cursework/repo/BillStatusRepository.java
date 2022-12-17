package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.BillStatus;

public interface BillStatusRepository extends JpaRepository<BillStatus, Long> {
}
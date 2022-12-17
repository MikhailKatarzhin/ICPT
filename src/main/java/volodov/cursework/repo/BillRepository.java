package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import volodov.cursework.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
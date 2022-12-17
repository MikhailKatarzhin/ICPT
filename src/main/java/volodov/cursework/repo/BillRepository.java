package volodov.cursework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volodov.cursework.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
}
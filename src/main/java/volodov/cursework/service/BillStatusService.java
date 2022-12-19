package volodov.cursework.service;

import org.springframework.stereotype.Service;
import volodov.cursework.model.BillStatus;
import volodov.cursework.repo.BillStatusRepository;

@Service
public class BillStatusService {

    private final BillStatusRepository billStatusRepository;

    public BillStatusService(BillStatusRepository billStatusRepository) {
        this.billStatusRepository = billStatusRepository;
    }

    public BillStatus getById(Long statusId){
        return billStatusRepository.getById(statusId);
    }
}

package volodov.cursework.service;

import org.springframework.stereotype.Service;
import volodov.cursework.model.Bill;
import volodov.cursework.model.Trip;
import volodov.cursework.repo.BillRepository;

import java.util.LinkedList;
import java.util.List;

import static volodov.cursework.config.ProjectConstants.BILL_CONSUMER_ROW_COUNT;
import static volodov.cursework.config.ProjectConstants.TRIP_ROW_COUNT;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final UserService userService;

    public BillService(BillRepository billRepository, UserService userService) {
        this.billRepository = billRepository;
        this.userService = userService;
    }

    public Bill getById(Long id){
        return billRepository.getById(id);
    }

    public Bill save(Bill bill){
        return billRepository.save(bill);
    }

    public void delete(Bill bill){
        billRepository.delete(bill);
    }

    public Long countById(long billId){
        return billRepository.countById(billId);
    }

    public Long countByConsumerId(long consumerId){
        return billRepository.countByConsumerId(consumerId);
    }

    public Long countByConsumerIdAndTripId(long consumerId, long tripId){
        return billRepository.countByConsumerIdAndTripId(consumerId, tripId);
    }

    public Long pageCountByRemoteConsumer(){
        return pageCountByConsumerId(userService.getRemoteUserId());
    }

    public Long pageCountByConsumerId(Long consumerId){
        long nPage = countByConsumerId(consumerId) / BILL_CONSUMER_ROW_COUNT + (countByConsumerId(consumerId) % BILL_CONSUMER_ROW_COUNT == 0 ? 0 : 1);
        return nPage == 0 ? nPage + 1 : nPage;
    }

    public List<Bill> findByRemoteConsumerAndLimitOffset(Long currentPage){
        return findByConsumerIdAndLimitOffset(currentPage, userService.getRemoteUserId());
    }

    public List<Bill> findByConsumerIdAndLimitOffset(Long currentPage, Long consumerId){
        if (countByConsumerId(consumerId) == 0)
            return new LinkedList<>();
        return billRepository.findByConsumerIdAndLimitOffset(consumerId, BILL_CONSUMER_ROW_COUNT, (currentPage - 1) * BILL_CONSUMER_ROW_COUNT);
    }

    public void updateAllByNewStatusIdAndTripId(Long newStatusId, Long tripId){
        billRepository.updateAllByNewStatusIdAndTripId(newStatusId, tripId);
    }
}

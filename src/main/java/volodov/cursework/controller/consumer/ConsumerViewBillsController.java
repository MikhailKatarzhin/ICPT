package volodov.cursework.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.controller.paging.AbstractPrimaryPagingController;
import volodov.cursework.model.Bill;
import volodov.cursework.model.Trip;
import volodov.cursework.service.BillService;
import volodov.cursework.service.TripService;
import volodov.cursework.service.TripStatusService;
import volodov.cursework.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/consumer/bills")
public class ConsumerViewBillsController extends AbstractPrimaryPagingController {

    private final UserService userService;
    private final BillService billService;

    public ConsumerViewBillsController(UserService userService, BillService billService) {
        this.userService = userService;
        this.billService = billService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping
    public String managementByOffset() {
        return "redirect:/consumer/bills/list/1";
    }

    @GetMapping("/list/{currentPage}")
    public String managementByOffset(@PathVariable Long currentPage, ModelMap model) {
        if (currentPage < 1L)
            return "redirect:/consumer/bills/list/1";
        Long nPage = pageCount();
        if (currentPage > nPage)
            return "redirect:/consumer/bills/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        List<Bill> bills = billService.findByRemoteConsumerAndLimitOffset(currentPage);
        model.addAttribute("bills", bills);
        return "consumer/bills";
    }

///********************! Pagination !********************

    @Override
    protected Long pageCount() {
        return billService.pageCountByRemoteConsumer();
    }

    @Override
    protected String getPrefix() {
        return "/consumer/bills";
    }
}

package volodov.cursework.controller.consumer;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.model.Bill;
import volodov.cursework.model.Location;
import volodov.cursework.model.RouteSequence;
import volodov.cursework.model.Trip;
import volodov.cursework.service.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/consumer/manage/bill")
public class ConsumerManageBillController {

    private final BillStatusService billStatusService;
    private final UserService userService;
    private final RouteSequenceService routeSequenceService;
    private final BillService billService;

    public ConsumerManageBillController(BillStatusService billStatusService, UserService userService, RouteSequenceService routeSequenceService, BillService billService) {
        this.billStatusService = billStatusService;
        this.userService = userService;
        this.routeSequenceService = routeSequenceService;
        this.billService = billService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping("/{billId}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @billService.getById(#billId).getConsumer().getId()) or hasAuthority('АДМИНИСТРАТОР')")
    public String managementById(@PathVariable Long billId, ModelMap model) {
        Bill bill = billService.getById(billId);
        model.addAttribute("routeSequences", routeSequenceService.getByTripId(bill.getTrip().getId()));
        model.addAttribute("bill", bill);
        return "consumer/billManage";
    }

    @PostMapping("/{billId}/setStatus/{statusId}")
    @PreAuthorize("@userService.getRemoteUser().getId() == @billService.getById(#billId).getConsumer().getId()")
    public String managementById(@PathVariable Long billId, @PathVariable Long statusId) {
        Bill bill = billService.getById(billId);
        if (bill.getStatus().getId() == 4 && statusId == 5)
            bill.setStatus(billStatusService.getById(statusId));
        billService.save(bill);
        return "redirect:/consumer/manage/bill/" + billId;
    }
}

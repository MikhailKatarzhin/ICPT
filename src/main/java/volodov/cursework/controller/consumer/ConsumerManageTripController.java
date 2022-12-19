package volodov.cursework.controller.consumer;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.model.Bill;
import volodov.cursework.model.Trip;
import volodov.cursework.service.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/consumer/manage/trip")
public class ConsumerManageTripController {

    private final BillStatusService billStatusService;
    private final TripService tripService;
    private final UserService userService;
    private final BillService billService;
    private final RouteSequenceService routeSequenceService;

    public ConsumerManageTripController(BillStatusService billStatusService, TripService tripService, UserService userService, BillService billService, RouteSequenceService routeSequenceService) {
        this.billStatusService = billStatusService;
        this.tripService = tripService;
        this.userService = userService;
        this.billService = billService;
        this.routeSequenceService = routeSequenceService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping("/{tripId}")
    @PreAuthorize("(hasAuthority('ПОТРЕБИТЕЛЬ'))")
    public String managementById(@PathVariable Long tripId, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        model.addAttribute("routeSequences", routeSequenceService.getByTripId(tripId));
        model.addAttribute("trip", trip);
        model.addAttribute("yourNBills", billService.countByConsumerIdAndTripId(userService.getRemoteUserId(), tripId));
        return "consumer/tripManage";
    }

    @Transactional
    @PostMapping("/{tripId}/billPay")
    @PreAuthorize("(hasAuthority('ПОТРЕБИТЕЛЬ'))")
    public String billPay(@PathVariable Long tripId, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        if (trip.getStatus().getId() != 2L || trip.getBills().size() >= trip.getPlaces())
            return "redirect:/consumer/manage/trip/" + tripId;
        Instant time = Instant.now().plus(30, ChronoUnit.MINUTES);
        if (trip.getDepartureTime().isBefore(time))
            return "redirect:/consumer/manage/trip/" + tripId;
        Bill bill = new Bill();
        bill.setConsumer(userService.getRemoteUser());
        bill.setTrip(trip);
        bill.setConsumer(userService.getRemoteUser());
        bill.setStatus(billStatusService.getById(1L));
        bill = billService.save(bill);
        trip = tripService.getById(tripId);
        if (trip.getBills().size() < trip.getPlaces()) {
            model.addAttribute("tripId", trip.getId());
            model.addAttribute("billId", bill.getId());
            return "consumer/paymentSuccess";
        }
        billService.delete(bill);
        return "redirect:/consumer/manage/trip/" + tripId;
    }
}

package volodov.cursework.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.controller.paging.AbstractPrimaryPagingController;
import volodov.cursework.model.Trip;
import volodov.cursework.service.TripService;
import volodov.cursework.service.TripStatusService;
import volodov.cursework.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/consumer/trips")
public class ConsumerViewAvailableTripsController extends AbstractPrimaryPagingController {

    private final TripStatusService tripStatusService;
    private final TripService tripService;
    private final UserService userService;

    public ConsumerViewAvailableTripsController(TripStatusService tripStatusService, TripService tripService, UserService userService) {
        this.tripStatusService = tripStatusService;
        this.tripService = tripService;
        this.userService = userService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping
    public String managementByOffset() {
        return "redirect:/consumer/trips/list/1";
    }

    @GetMapping("/list/{currentPage}")
    public String managementByOffset(@PathVariable Long currentPage, ModelMap model) {
        if (currentPage < 1L)
            return "redirect:/consumer/trips/list/1";
        Long nPage = pageCount();
        if (currentPage > nPage)
            return "redirect:/consumer/trips/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        List<Trip> tripList = tripService.findAvailableTripsByLimitOffset(currentPage);
        model.addAttribute("trips", tripList);
        return "consumer/trips";
    }

///********************! Pagination !********************

    @Override
    protected Long pageCount() {
        return tripService.pageCountAvailable();
    }

    @Override
    protected String getPrefix() {
        return "/consumer/trips";
    }
}

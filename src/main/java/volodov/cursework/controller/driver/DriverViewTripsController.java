package volodov.cursework.controller.driver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import volodov.cursework.controller.paging.AbstractPrimaryPagingController;
import volodov.cursework.model.Trip;
import volodov.cursework.service.TripService;
import volodov.cursework.service.TripStatusService;
import volodov.cursework.service.UserService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/driver/trips")
public class DriverViewTripsController extends AbstractPrimaryPagingController {

    private final TripStatusService tripStatusService;
    private final TripService tripService;
    private final UserService userService;

    public DriverViewTripsController(TripStatusService tripStatusService, TripService tripService, UserService userService) {
        this.tripStatusService = tripStatusService;
        this.tripService = tripService;
        this.userService = userService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping
    public String managementByOffset() {
        return "redirect:/driver/trips/list/1";
    }

    @GetMapping("/list/{currentPage}")
    public String managementByOffset(@PathVariable Long currentPage, ModelMap model) {
        if (currentPage < 1L)
            return "redirect:/driver/trips/list/1";
        Long nPage = pageCount();
        if (currentPage > nPage)
            return "redirect:/driver/trips/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        List<Trip> tripList = tripService.findByRemoteDriverAndLimitOffset(currentPage);
        model.addAttribute("trips", tripList);
        return "driver/trips";
    }

    @GetMapping("/add")
    public String driverAddForm(ModelMap model) {
        Instant departureTime = Instant.now().plus(30, ChronoUnit.MINUTES);
        String time = departureTime.toString();
        Short places = 1;
        Long cost = 0L;
        model.addAttribute("time", departureTime);
        model.addAttribute("places", places);
        model.addAttribute("cost", cost);
        return "driver/addTrip";
    }

    @PostMapping("/add")
    public String driverAdd(String time, Short places, Long cost, ModelMap model) {
        Trip trip = new Trip();
        time = time + ":00.000000z";
        trip.setDepartureTime(Instant.parse(time));
        if (trip.getDepartureTime().isBefore(Instant.now().plus(30, ChronoUnit.MINUTES))) {
            model.addAttribute("invalidDepartureTime", "Неверное время отправления - осталось меньше получаса");
            return "driver/addTrip";
        }
        trip.setPlaces(places);
        trip.setCost(cost);
        trip.setStatus(tripStatusService.getById(1L));
        trip.setDriver(userService.getRemoteUser());
        tripService.save(trip);
        return "redirect:/driver/trips/list/1";
    }

///********************! Pagination !********************

    @Override
    protected Long pageCount() {
        return tripService.pageCountByRemoteDriver();
    }

    @Override
    protected String getPrefix() {
        return "/driver/trips";
    }
}

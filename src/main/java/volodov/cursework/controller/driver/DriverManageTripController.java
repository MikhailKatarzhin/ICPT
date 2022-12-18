package volodov.cursework.controller.driver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.model.Location;
import volodov.cursework.model.RouteSequence;
import volodov.cursework.model.Trip;
import volodov.cursework.service.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/driver/manage/trip")
public class DriverManageTripController {

    private final TripStatusService tripStatusService;
    private final TripService tripService;
    private final UserService userService;
    private final LocationService locationService;
    private final RouteSequenceService routeSequenceService;

    public DriverManageTripController(TripStatusService tripStatusService, TripService tripService, UserService userService, LocationService locationService, RouteSequenceService routeSequenceService) {
        this.tripStatusService = tripStatusService;
        this.tripService = tripService;
        this.userService = userService;
        this.locationService = locationService;
        this.routeSequenceService = routeSequenceService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping("/{tripId}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @tripService.getById(#tripId).getDriver().getId()) or hasAuthority('АДМИНИСТРАТОР')")
    public String managementById(@PathVariable Long tripId, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        model.addAttribute("routeSequences", routeSequenceService.getByTripId(tripId));
        model.addAttribute("trip", trip);
        return "driver/tripManage";
    }

    @PostMapping("/{tripId}/setStatus/{statusId}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @tripService.getById(#tripId).getDriver().getId())")
    public String managementById(@PathVariable Long tripId, @PathVariable Long statusId, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        if (trip.getStatus().getId() != 5)
            if (statusId - trip.getStatus().getId() == 1)
                trip.setStatus(tripStatusService.getById(statusId));
        if (statusId == 5 && trip.getStatus().getId() < 4)
            trip.setStatus(tripStatusService.getById(statusId));
        tripService.save(trip);
        model.addAttribute("trip", trip);
        return "driver/tripManage";
    }


    @GetMapping("/{tripId}/addLocation")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @tripService.getById(#tripId).getDriver().getId())")
    public String getAddLocationForm(@PathVariable Long tripId, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        if (trip.getStatus().getId() != 1L)
            return "refirect:/driver/manage/trip/" + tripId;
        Instant arrivalTime = Instant.now().plus(30, ChronoUnit.MINUTES);
        model.addAttribute("trip", trip);
        model.addAttribute("time", arrivalTime);
        model.addAttribute("locationName", "");
        return "driver/addLocation";
    }

    @PostMapping("/{tripId}/addLocation")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @tripService.getById(#tripId).getDriver().getId())")
    public String addLocation(@PathVariable Long tripId, String time, String locationName, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        Location location = locationService.findOneLikeLocationName(locationName);
        if (trip.getStatus().getId() != 1L || location == null)
            return "redirect:/driver/manage/trip/" + tripId;
        RouteSequence routeSequence = new RouteSequence();
        routeSequence.setArrivalTime(Instant.parse(time+ ":00.000000z"));
        if (trip.getDepartureTime().isAfter(routeSequence.getArrivalTime().minus(5, ChronoUnit.MINUTES))) {
            model.addAttribute("invalidDepartureTime", "Неверное время прибытия - менее пяти минут от отправления");
            return "driver/addLocation";
        }
        routeSequence.setLocation(location);
        routeSequence.setTrip(trip);
        routeSequenceService.save(routeSequence);
        return "redirect:/driver/manage/trip/" + tripId;
    }
}

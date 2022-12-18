package volodov.cursework.controller.driver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.model.Trip;
import volodov.cursework.service.TripService;
import volodov.cursework.service.TripStatusService;
import volodov.cursework.service.UserService;

@Controller
@RequestMapping("/driver/manage/trip")
public class DriverManageTripController {

    private final TripStatusService tripStatusService;
    private final TripService tripService;
    private final UserService userService;

    public DriverManageTripController(TripStatusService tripStatusService, TripService tripService, UserService userService) {
        this.tripStatusService = tripStatusService;
        this.tripService = tripService;
        this.userService = userService;
    }
///********************! Управление поездками водителей !********************

    @GetMapping("/{tripId}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @tripService.getById(#tripId).getDriver().getId()) or hasAuthority('АДМИНИСТРАТОР')")
    public String managementById(@PathVariable Long tripId, ModelMap model) {
        Trip trip = tripService.getById(tripId);
        model.addAttribute("trip", trip);
        return "driver/tripManage";
    }

    @PostMapping("/{tripId}/setStatus/{statusId}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == @tripService.getById(#tripId).getDriver().getId()) or hasAuthority('АДМИНИСТРАТОР')")
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
}

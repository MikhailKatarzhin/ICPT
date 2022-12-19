package volodov.cursework.controller.Location;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import volodov.cursework.controller.paging.AbstractPrimaryPagingController;
import volodov.cursework.model.Location;
import volodov.cursework.service.LocationService;

import java.util.List;

@Controller
@RequestMapping("/home/locations")
public class LocationViewController extends AbstractPrimaryPagingController {

    private final LocationService locationService;

    public LocationViewController(LocationService locationService) {
        this.locationService = locationService;
    }

///********************! Управление локациями !********************

    @GetMapping
    public String managementByOffset() {
        return "redirect:/home/locations/list/1";
    }

    @GetMapping("/list/{currentPage}")
    public String managementByOffset(@PathVariable Long currentPage, Location locationSearch, ModelMap model) {
        String locationName = locationSearch.getName();
        if (locationName == null) {
            locationName = "";
            locationSearch.setName(locationName);
        }
        else if (locationName.equals("Любой") || locationName.equals("любой")
                || locationName.equals("Любая") || locationName.equals("любая")
                || locationName.equals("Любое") || locationName.equals("любое")
                || locationName.isBlank() || locationName.isEmpty()
                || !locationName.matches("[А-Яа-яЁё ,.-]{1,45}"))
            locationName = "";
        model.addAttribute("locationSearch", locationSearch);
        if (currentPage < 1L)
            return "redirect:/home/locations/list/1";
        Long nPage = locationService.pageCountLikeName(locationName);
        if (currentPage > nPage)
            return "redirect:/home/locations/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        model.remove("locations");
        List<Location> locationList = locationService.findLikeLocationNameByNumberPage(locationName, currentPage);
        model.addAttribute("locations", locationList);
        return "home/locations";
    }

///********************! Pagination !********************

    @Override
    protected Long pageCount() {
        return locationService.pageCount();
    }

    @Override
    protected String getPrefix() {
        return "/home/locations";
    }
}

package volodov.cursework.controller.administration;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import volodov.cursework.controller.paging.AbstractPrimaryPagingController;
import volodov.cursework.model.Location;
import volodov.cursework.service.LocationService;

import java.util.List;

@Controller
@RequestMapping("/administration/locations")
public class AdministrationLocationController extends AbstractPrimaryPagingController {

    private final LocationService locationService;

    public AdministrationLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

///********************! Управление локациями !********************

    @GetMapping
    public String managementByOffset() {
        return "redirect:/administration/locations/list/1";
    }

    @GetMapping("/list/{currentPage}")
    public String managementByOffset(@PathVariable Long currentPage, Location locationSearch, Location locationNew, ModelMap model) {
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
        model.addAttribute("locationNew", locationNew);
        if (currentPage < 1L)
            return "redirect:/administration/locations/list/1";
        Long nPage = locationService.pageCountLikeName(locationName);
        if (currentPage > nPage)
            return "redirect:/administration/locations/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        model.remove("locations");
        List<Location> locationList = locationService.findLikeLocationNameByNumberPage(locationName, currentPage);
        model.addAttribute("locations", locationList);
        return "administration/locations";
    }

    @PostMapping("/list/{currentPage}")
    public String managementByOffsetAdd(@PathVariable Long currentPage, Location locationSearch, Location locationNew, ModelMap model) {
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
        if (locationNew.getName() != null)
            if (locationNew.getName().matches("[А-Яа-яЁё ,.-]{4,45}"))
                locationService.save(locationNew);
        model.addAttribute("locationNew", locationNew);
        if (currentPage < 1L)
            return "redirect:/administration/locations/list/1";
        Long nPage = locationService.pageCountLikeName(locationName);
        if (currentPage > nPage)
            return "redirect:/administration/locations/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        model.remove("locations");
        List<Location> locationList = locationService.findLikeLocationNameByNumberPage(locationName, currentPage);
        model.addAttribute("locations", locationList);
        return "administration/locations";
    }

///********************! Pagination !********************

    @Override
    protected Long pageCount() {
        return locationService.pageCount();
    }

    @Override
    protected String getPrefix() {
        return "/administration/locations";
    }
}

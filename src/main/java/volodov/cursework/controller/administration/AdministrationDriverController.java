package volodov.cursework.controller.administration;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import volodov.cursework.controller.paging.AbstractPrimaryPagingController;
import volodov.cursework.model.Personality;
import volodov.cursework.model.User;
import volodov.cursework.service.PersonalityService;
import volodov.cursework.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/administration/drivers")
public class AdministrationDriverController extends AbstractPrimaryPagingController {

    private final UserService userService;
    private final PersonalityService personalityService;

    public AdministrationDriverController(UserService userService, PersonalityService personalityService) {
        this.userService = userService;
        this.personalityService = personalityService;
    }
///********************! Управление водителями !********************

    @GetMapping
    public String managementByOffset() {
        return "redirect:/administration/drivers/list/1";
    }

    @GetMapping("/list/{currentPage}")
    public String managementByOffset(@PathVariable Long currentPage, ModelMap model) {
        if (currentPage < 1L)
            return "redirect:/administration/drivers/list/1";
        Long nPage = pageCount();
        if (currentPage > nPage)
            return "redirect:/administration/drivers/list/" + nPage;
        model.addAttribute("nPage", nPage);
        model.addAttribute("currentPage", currentPage);
        List<User> driverList = userService.driverListByNumberPageList(currentPage);
        model.addAttribute("drivers", driverList);
        return "administration/drivers";
    }

    @GetMapping("/add")
    public String driverAddForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("personality", new Personality());
        return "administration/addDriver";
    }

    @Transactional
    @PostMapping("/add")
    public String driverAdd(@RequestParam String confirmPassword, User user, Personality personality, ModelMap model) {

        if (userService.getByUsername(user.getUsername()) != null || !user.getUsername().matches("[A-Za-z0-9 А-Яа-я]{3,45}")) {
            model.addAttribute("usernameExistsError", "Username input error");
        }

        if (user.getPassword().isBlank() || !user.getPassword().matches("[A-Za-z0-9#$&/%-._]{8,60}$")) {
            model.addAttribute("passwordIsBlankError", "Password input error!");
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordsAreDifferent", "Passwords are different");
        }

        if (userService.emailExists(user.getEmail()) || !user.getEmail().matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            model.addAttribute("emailExistsError", "Email input error");
        }
        if (!(personality.getSeriesAndNumber().matches("[0-9]{10}"))) {
            model.addAttribute("personalityExistsError", "Series and number input error! Только 10 цифр");
        } else if (personalityService.getBySeriesAndNumber(personality.getSeriesAndNumber()) != null) {
            model.addAttribute("personalityExistsError", "Series and number input error! Только 10 цифр");
        }

        if (personality.getFirstname().isBlank() || personality.getFirstname() == null || !personality.getFirstname().matches("[ А-Яа-я]{2,45}$")) {
            model.addAttribute("firstnameIsBlankError", "Firstname input error! Только кириллица, от 2 до 35");
        }

        if (personality.getLastname().isBlank() || personality.getLastname() == null || !personality.getLastname().matches("[ А-Яа-я]{2,45}$")) {
            model.addAttribute("lastnameIsBlankError", "Lastname input error! Только кириллица, от 2 до 35");
        }

        if (personality.getPatronymic().isBlank() || personality.getPatronymic() == null)
            personality.setPatronymic("Отсутствует");
        if (personality.getPatronymic().isEmpty() || personality.getLastname() == null || !personality.getPatronymic().matches("[ А-Яа-я]{2,45}$")) {
            model.addAttribute("PatronymicIsInputError", "Patronymic input error! Только кириллица или ничего");
        }        if (model.size() > 4) {
            return "administration/addDriver";
        }
        userService.signUpDriver(user, personality);
        return "redirect:/administration/drivers/list/1";
    }

///********************! Pagination !********************

    @Override
    protected Long pageCount() {
        return userService.pageCountByRoleId(2L);
    }

    @Override
    protected String getPrefix() {
        return "/administration/drivers";
    }
}

package volodov.cursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import volodov.cursework.model.Personality;
import volodov.cursework.model.User;
import volodov.cursework.service.PersonalityService;
import volodov.cursework.service.UserService;

@RequestMapping("/sign_up")
@Controller
public class SignUpController {

    private final UserService userService;
    private final PersonalityService personalityService;

    @Autowired
    public SignUpController(UserService userService, PersonalityService personalityService) {
        this.userService = userService;
        this.personalityService = personalityService;
    }

    @GetMapping
    public String signUp(ModelMap model) {
        if (userService.getRemoteUser() != null)
            return "redirect:/profile";
        model.addAttribute("user", new User());
        model.addAttribute("personality", new Personality());
        return "sign_up";
    }

    @Transactional
    @PostMapping
    public String addUser(@RequestParam String confirmPassword, User user, Personality personality, ModelMap model) {

        model = checkRegistrationData(confirmPassword, user, personality, model, userService, personalityService);
        if (model.size() > 4) {
            return "sign_up";
        }
        userService.signUpConsumer(user, personality);
        return "redirect:/sign_in";
    }

    public static ModelMap checkRegistrationData(@RequestParam String confirmPassword, User user, Personality personality, ModelMap model, UserService userService, PersonalityService personalityService) {
        if (userService.getByUsername(user.getUsername()) != null || !user.getUsername().matches("[A-Za-z0-9 А-Яа-яЁё-]{3,45}")) {
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

        if (personality.getFirstname().isBlank() || personality.getFirstname() == null || !personality.getFirstname().matches("[ А-Яа-яЁё]{2,45}$")) {
            model.addAttribute("firstnameIsBlankError", "Firstname input error! Только кириллица, от 2 до 35");
        }

        if (personality.getLastname().isBlank() || personality.getLastname() == null || !personality.getLastname().matches("[ А-Яа-яЁё-]{2,45}$")) {
            model.addAttribute("lastnameIsBlankError", "Lastname input error! Только кириллица, от 2 до 35");
        }

        if (personality.getPatronymic().isBlank() || personality.getPatronymic() == null)
            personality.setPatronymic("Отсутствует");
        if (personality.getPatronymic().isEmpty() || personality.getLastname() == null || !personality.getPatronymic().matches("[ А-Яа-яЁё]{2,45}$")) {
            model.addAttribute("PatronymicIsInputError", "Patronymic input error! Только кириллица или ничего");
        }
        return model;
    }
}

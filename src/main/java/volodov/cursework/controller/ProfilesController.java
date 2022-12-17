package volodov.cursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import volodov.cursework.model.Personality;
import volodov.cursework.model.User;
import volodov.cursework.service.PersonalityService;
import volodov.cursework.service.UserService;

@RequestMapping("/profile")
@Controller
public class ProfilesController {
    private final PersonalityService personalityService;
    private final UserService userService;

    @Autowired
    public ProfilesController(PersonalityService personalityService, UserService userService) {
        this.personalityService = personalityService;
        this.userService = userService;
    }

    @GetMapping
    public String myProfile() {
        Long userId = userService.getRemoteUserId();
        return "redirect:/profile/" + userId;
    }
    @PostMapping
    public String myProfileP() {
        Long userId = userService.getRemoteUserId();
        return "redirect:/profile/" + userId;
    }

    @GetMapping("/{id}")
    @PreAuthorize("(@userService.getRemoteUser().getId() == #id) or hasAuthority('АДМИНИСТРАТОР')")
    public String viewProfileById(@PathVariable Long id, ModelMap model) {
        User user = userService.getById(id);
        if (user == null)
            return myProfile();
        model.addAttribute("user", user);
        model.addAttribute("personality", personalityService.getByUserId(id));
        return "profile/profile";
    }

    @GetMapping("/change_email")
    public String changeEmail(ModelMap model) {
        model.addAttribute("currentEmail", userService.getRemoteUserEmail());
        return "profile/change_email";
    }

    @PostMapping("/change_email")
    public String changeEmail(@RequestParam("email") String email, ModelMap model) {
        if (userService.emailExists(email)
                || !email.matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            model.addAttribute("currentEmail", userService.getRemoteUserEmail());
            model.addAttribute("emailInputError", "Email уже существует или введён неверно");
            return "profile/change_email";
        }
        if (userService.emailExists(email)) {
            model.addAttribute("emailExistsError", "Email already exists");
            return "profile/change_email";
        }
        userService.saveEmail(email);
        return "redirect:/profile";
    }

    @GetMapping("/change_password")
    public String changePassword() {
        return "profile/change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(
            @RequestParam("newPassword") String newPassword
            , @RequestParam("currentPassword") String currentPassword
            , @RequestParam("confirmPassword") String confirmPassword, ModelMap model
    ) {
        if (!userService.checkRemoteUserPassword(currentPassword)) {
            model.addAttribute("InvalidPasswordError", "Установленный пароль неверено введен");
            return "profile/change_password";
        }
        if (newPassword.isBlank() || !newPassword.matches("[A-Za-z0-9#$&/%-._]{8,60}$")) {
            model.addAttribute("passwordIsBlankError", "Новый пароль введен некорректно");
            return "profile/change_password";
        }
        if (!confirmPassword.equals(newPassword)) {
            model.addAttribute("currentPassword", currentPassword);
            model.addAttribute("passwordIsDifferentError", "Пароли различаются");
            return "profile/change_password";
        }
        userService.savePassword(newPassword);
        return "redirect:/profile";
    }

    @GetMapping("/change_personality")
    public String changePersonality(ModelMap model) {
        model.addAttribute("personality", userService.getRemoteUserPersonality());
        return "profile/change_personality";
    }

    @PostMapping("/change_personality")
    public String changePersonality(@ModelAttribute Personality personality, ModelMap model) {
        personalityService.save(personality);
        return "redirect:/profile";
    }
}

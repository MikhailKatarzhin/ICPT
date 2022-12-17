package volodov.cursework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import volodov.cursework.config.WebSecurityConfig;
import volodov.cursework.model.Personality;
import volodov.cursework.model.User;
import volodov.cursework.repo.RoleRepository;
import volodov.cursework.repo.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService{

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PersonalityService personalityService;

    @Autowired
    public UserService(UserRepository userRepository, HttpServletRequest httpServletRequest
            , BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, PersonalityService personalityService) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.personalityService = personalityService;
    }

    public User getRemoteUser() {
        return userRepository.findByUsername(httpServletRequest.getRemoteUser());
    }

    public Long getRemoteUserId() {
        return getRemoteUser().getId();
    }

    public String getRemoteUserEmail() {
        return getRemoteUser().getEmail();
    }

    public Personality getRemoteUserPersonality() {
        return getRemoteUser().getPersonality();
    }

    public boolean checkRemoteUserPassword(String password) {
        return WebSecurityConfig.encoder().matches(password, getRemoteUser().getPassword());
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User signUpConsumer(User user, Personality personality){
        return  signUp(user, personality, 1L);
    }

    public User signUpDriver(User user, Personality personality){
        return  signUp(user, personality, 2L);
    }

    public User signUp(User user, Personality personality, long roleId) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.getById(roleId));
        user = userRepository.save(user);
        personality.setUser(user);
        personalityService.save(personality);
        logger.info("Signed up new user [id:{}] with role: {}", user.getId()
                , user.getRole());
        return user;
    }

    public boolean emailExists(String email) {
        return userRepository.countByEmail(email) != 0;
    }

    public User saveEmail(String email) {
        User user = getRemoteUser();
        String oldEmail = user.getEmail();
        user.setEmail(email);
        user =  userRepository.save(user);
        logger.info("User {}[id:{}] change email {} to {}", user.getUsername(), user.getId(), oldEmail, user.getEmail());
        return user;
    }

    public User savePassword(String password) {
        User user = getRemoteUser();
        user.setPassword(WebSecurityConfig.encoder().encode(password));
        user = userRepository.save(user);
        logger.info("User {}[id:{}] change password to {}", user.getUsername(), user.getId(), password);
        return user;
    }
}

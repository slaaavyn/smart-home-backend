package tk.slaaavyn.slavikhomebackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tk.slaaavyn.slavikhomebackend.model.Role;
import tk.slaaavyn.slavikhomebackend.model.User;
import tk.slaaavyn.slavikhomebackend.repo.UserRepository;
import tk.slaaavyn.slavikhomebackend.security.SecurityConstants;
import tk.slaaavyn.slavikhomebackend.service.UserService;

@Component
public class DbInitializer implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    @Value("${default.admin.username}")
    private String defaultUsername;

    @Value("${default.admin.password}")
    private String defaultPassword;

    private final UserService userService;

    public DbInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        initUser();
    }

    private void initUser() {
        if (userService.getAllAdmins().size() != 0) {
            return;
        }

        User user = new User();
        user.setUsername(defaultUsername);
        user.setPassword(defaultPassword);
        user.setRole(Role.ROLE_ADMIN);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        userService.createUser(user);

        logger.info("ADMIN has been initialized");
    }
}

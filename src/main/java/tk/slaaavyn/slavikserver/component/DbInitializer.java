package tk.slaaavyn.slavikserver.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tk.slaaavyn.slavikserver.model.Role;
import tk.slaaavyn.slavikserver.model.User;
import tk.slaaavyn.slavikserver.repo.RoleRepository;
import tk.slaaavyn.slavikserver.repo.UserRepository;
import tk.slaaavyn.slavikserver.security.SecurityConstants;

@Component
public class DbInitializer implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DbInitializer(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initRoles();
        initUser();
    }

    private void initRoles() {
        if(roleRepository.findAll().size() != 0) {
            return;
        }

        Role roleAdmin = new Role();
        roleAdmin.setName(SecurityConstants.ROLE_ADMIN);
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName(SecurityConstants.ROLE_USER);
        roleRepository.save(roleUser);

        logger.info("ROLES has been initialized");
    }

    private void initUser() {
        if (userRepository.findAllByRole_Name(SecurityConstants.ROLE_ADMIN).size() != 0) {
            return;
        }

        Role roleAdmin = roleRepository.findRoleByName(SecurityConstants.ROLE_ADMIN);
        if (roleAdmin == null) {
            initRoles();
            roleAdmin = roleRepository.findRoleByName(SecurityConstants.ROLE_ADMIN);
        }

        User user = new User();
        user.setUsername("root");
        user.setPassword(passwordEncoder.encode("toor"));
        user.setRole(roleAdmin);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        userRepository.save(user);

        logger.info("ADMIN has been initialized");
    }
}

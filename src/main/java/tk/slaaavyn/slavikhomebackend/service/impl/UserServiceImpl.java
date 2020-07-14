package tk.slaaavyn.slavikhomebackend.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikhomebackend.dto.user.UpdatePasswordDto;
import tk.slaaavyn.slavikhomebackend.exception.ApiRequestException;
import tk.slaaavyn.slavikhomebackend.exception.ConflictException;
import tk.slaaavyn.slavikhomebackend.exception.NotFoundException;
import tk.slaaavyn.slavikhomebackend.model.Role;
import tk.slaaavyn.slavikhomebackend.model.User;
import tk.slaaavyn.slavikhomebackend.repo.UserRepository;
import tk.slaaavyn.slavikhomebackend.security.SecurityConstants;
import tk.slaaavyn.slavikhomebackend.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User incomingUser) {
        if(isUserAlreadyExist(incomingUser.getUsername())) {
            throw new ConflictException("User with username: " + incomingUser.getUsername() + " already exist");
        }

        incomingUser.setPassword(passwordEncoder.encode(incomingUser.getPassword()));

        return userRepository.save(incomingUser);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByRole(Role.ROLE_USER);
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepository.findAllByRole(Role.ROLE_ADMIN);
    }

    @Override
    public User getById(long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("user with id :" + userId + " not found"));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("user with username :" + username + " not found"));
    }

    @Override
    public User updateUserInfo(long userId, User incomingUser) {
        User updatableUser = getById(userId);

        updatableUser.setFirstName(incomingUser.getFirstName() != null
                ? incomingUser.getFirstName() : updatableUser.getFirstName());

        updatableUser.setLastName(incomingUser.getLastName() != null
                ? incomingUser.getLastName() : updatableUser.getLastName());

        return userRepository.save(updatableUser);
    }

    @Override
    public User updateUserRole(long userId, Role newRole) {
        User user = getById(userId);

        if(isLastAdmin(user)) {
            throw new ApiRequestException("it is not possible to change role for a single administrator");
        }

        user.setRole(newRole);
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(long userId, UpdatePasswordDto passwordDto) {
        User user = getById(userId);

        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())){
            throw new ConflictException("current password not matches");
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeById(long userId) {
        User user = getById(userId);

        if(isLastAdmin(user)) {
            throw new ApiRequestException("it is not possible to remove a single administrator");
        }

        userRepository.delete(user);
    }

    private boolean isUserAlreadyExist(String  username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    private boolean isLastAdmin(User user) {
        return user.getRole() == Role.ROLE_ADMIN && userRepository.findAllByRole(Role.ROLE_ADMIN).size() == 1;
    }
}

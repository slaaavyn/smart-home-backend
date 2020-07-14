package tk.slaaavyn.slavikhomebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.dto.user.UpdatePasswordDto;
import tk.slaaavyn.slavikhomebackend.dto.user.UpdateUserInfoDto;
import tk.slaaavyn.slavikhomebackend.dto.user.UserRequestDto;
import tk.slaaavyn.slavikhomebackend.dto.user.UserResponseDto;
import tk.slaaavyn.slavikhomebackend.exception.ApiRequestException;
import tk.slaaavyn.slavikhomebackend.model.Role;
import tk.slaaavyn.slavikhomebackend.model.User;
import tk.slaaavyn.slavikhomebackend.security.jwt.JwtUser;
import tk.slaaavyn.slavikhomebackend.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.USER_ENDPOINT)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userDto) {
        User result = userService.createUser(userDto.fromDto());

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> result = new ArrayList<>();

        userService.getAll().forEach(user -> result.add(UserResponseDto.toDto(user)));

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);

        return ResponseEntity.ok(UserResponseDto.toDto(user));
    }

    @PutMapping("/update-info/{id}")
    public ResponseEntity<UserResponseDto> updateInfo(@PathVariable(name = "id") Long id,
                                                       @RequestBody @Valid UpdateUserInfoDto userDto) {

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), Role.ROLE_USER.name()) && !jwtUser.getId().equals(id)) {
            throw new ApiRequestException("user cannot update info not belonging to his account");
        }

        User result = userService.updateUserInfo(id, userDto.fromDto());

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Object> updatePassword(@RequestBody @Valid UpdatePasswordDto passwordDto) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        userService.updatePassword(jwtUser.getId(), passwordDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<UserResponseDto> updateRole(@PathVariable(name = "id") Long id,
                                              @RequestParam("role") Role roleName) {
        User result = userService.updateUserRole(id, roleName);

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") Long id) {
        userService.removeById(id);
        return ResponseEntity.ok().build();
    }

}

package tk.slaaavyn.slavikserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikserver.config.EndpointConstants;
import tk.slaaavyn.slavikserver.dto.user.UpdatePasswordDto;
import tk.slaaavyn.slavikserver.dto.user.UserRequestDto;
import tk.slaaavyn.slavikserver.dto.user.UserResponseDto;
import tk.slaaavyn.slavikserver.model.User;
import tk.slaaavyn.slavikserver.security.SecurityConstants;
import tk.slaaavyn.slavikserver.security.jwt.JwtUser;
import tk.slaaavyn.slavikserver.service.UserService;

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
    public ResponseEntity<UserResponseDto> createAdmin(@RequestBody UserRequestDto userDto) {
        User result = userService.createUser(userDto.fromDto());

        if(result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> result = new ArrayList<>();

        userService.getAll().forEach(user -> result.add(UserResponseDto.toDto(user)));

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserResponseDto> getAdminById(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(user));
    }

    @PutMapping("/update-info/{id}")
    public ResponseEntity<UserResponseDto> updateAdmin(@PathVariable(name = "id") Long id,
                                                       @RequestBody UserRequestDto userDto) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), SecurityConstants.ROLE_USER) && !jwtUser.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        User result = userService.updateUserInfo(id, userDto.fromDto());

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<Object> updateAdmin(@PathVariable(name = "id") Long id,
                                                       @RequestBody UpdatePasswordDto passwordDto) {

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), SecurityConstants.ROLE_USER) && !jwtUser.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        boolean isSuccess = userService.updatePassword(id, passwordDto);

        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<UserResponseDto> updateAdminPassword(@PathVariable(name = "id") Long id,
                                              @RequestParam("roleName") String roleName) {
        User result = userService.updateUserRole(id, roleName);

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable(name = "id") Long id) {
        boolean isSuccess = userService.removeById(id);
        if(!isSuccess){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}

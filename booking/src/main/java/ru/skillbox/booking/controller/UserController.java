package ru.skillbox.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.dto.user.UserRequest;
import ru.skillbox.booking.dto.user.UserResponse;
import ru.skillbox.booking.mapper.UserMapper;
import ru.skillbox.booking.model.RoleType;
import ru.skillbox.booking.model.User;
import ru.skillbox.booking.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request,
                                               @RequestParam String role) {
        User user = userMapper.toEntity(request);
        user.setRole(RoleType.valueOf(role));
        return ResponseEntity.ok(userMapper.toResponse(userService.create(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setId(id);
        return ResponseEntity.ok(userMapper.toResponse(userService.update(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

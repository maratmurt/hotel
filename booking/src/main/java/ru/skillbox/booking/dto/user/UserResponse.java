package ru.skillbox.booking.dto.user;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}

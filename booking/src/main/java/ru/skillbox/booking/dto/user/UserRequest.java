package ru.skillbox.booking.dto.user;

public record UserRequest(
        String name,
        String password,
        String email
) {
}

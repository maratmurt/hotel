package ru.skillbox.booking.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.booking.dto.user.UserRequest;
import ru.skillbox.booking.dto.user.UserResponse;
import ru.skillbox.booking.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

}

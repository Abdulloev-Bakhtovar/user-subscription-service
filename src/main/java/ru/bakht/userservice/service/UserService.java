package ru.bakht.userservice.service;

import ru.bakht.userservice.dto.GetUserDto;
import ru.bakht.userservice.dto.UserDto;
import ru.bakht.userservice.entity.User;

import java.util.UUID;

public interface UserService {

    User getUserEntity(UUID userId);

    GetUserDto getById(UUID userId);

    GetUserDto create(UserDto userDto);

    GetUserDto update(UUID userId, UserDto userDto);

    void delete(UUID userId);
}

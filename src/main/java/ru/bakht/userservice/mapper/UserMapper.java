package ru.bakht.userservice.mapper;

import ru.bakht.userservice.dto.GetUserDto;
import ru.bakht.userservice.dto.UserDto;
import ru.bakht.userservice.entity.User;

public interface UserMapper {

    User updateEntity(UserDto userDto);

    GetUserDto toDto(User user);

    void updateEntity(UserDto userDto, User user);
}

package ru.bakht.userservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.bakht.userservice.dto.GetUserDto;
import ru.bakht.userservice.dto.UserDto;
import ru.bakht.userservice.entity.User;
import ru.bakht.userservice.exception.AppException;
import ru.bakht.userservice.mapper.SubscriptionMapper;
import ru.bakht.userservice.mapper.UserMapper;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final SubscriptionMapper subMapper;

    @Override
    public User updateEntity(UserDto userDto) {
        if (userDto == null) {
            log.error("Cannot update User entity: userDto is null");
            throw new AppException("UserDto must not be null", HttpStatus.BAD_REQUEST);
        }

        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    @Override
    public void updateEntity(UserDto userDto, User user) {
        if (userDto == null || user == null) {
            log.error("Cannot update User: userDto or user is null");
            throw new AppException("UserDto and User must not be null", HttpStatus.BAD_REQUEST);
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
    }

    @Override
    public GetUserDto toDto(User user) {
        if (user == null) {
            log.error("Cannot map User to DTO: user is null");
            throw new AppException("User must not be null", HttpStatus.NOT_FOUND);
        }

        return GetUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .subscriptionDtos(
                        user.getSubscriptions() == null ?
                                Collections.emptyList() :
                                subMapper.toDtoList(user.getSubscriptions())
                )
                .build();
    }
}

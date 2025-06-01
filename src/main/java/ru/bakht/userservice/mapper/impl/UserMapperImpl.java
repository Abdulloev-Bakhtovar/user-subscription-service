package ru.bakht.userservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bakht.userservice.dto.GetUserDto;
import ru.bakht.userservice.dto.UserDto;
import ru.bakht.userservice.entity.User;
import ru.bakht.userservice.mapper.SubscriptionMapper;
import ru.bakht.userservice.mapper.UserMapper;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final SubscriptionMapper subMapper;

    @Override
    public User updateEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    @Override
    public void updateEntity(UserDto userDto, User user) {

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
    }

    @Override
    public GetUserDto toDto(User user) {
        if (user == null) {
            return null;
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

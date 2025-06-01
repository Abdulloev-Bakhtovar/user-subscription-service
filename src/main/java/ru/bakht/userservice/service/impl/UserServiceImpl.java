package ru.bakht.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakht.userservice.dto.GetUserDto;
import ru.bakht.userservice.dto.UserDto;
import ru.bakht.userservice.entity.User;
import ru.bakht.userservice.exception.AppException;
import ru.bakht.userservice.mapper.UserMapper;
import ru.bakht.userservice.repository.UserRepo;
import ru.bakht.userservice.service.UserService;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public User getUserEntity(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new AppException(
                            "User not found with ID: " + userId,
                            HttpStatus.NOT_FOUND
                    );
                });
    }

    @Override
    public GetUserDto getById(UUID userId) {

        User user = getUserEntity(userId);

        return userMapper.toDto(user);
    }

    @Override
    public GetUserDto create(UserDto userDto) {
        log.info("Attempting to create new user with email: {}", userDto.getEmail());

        validateEmailUniqueness(userDto.getEmail());

        User user = userMapper.updateEntity(userDto);
        user = userRepo.save(user);

        log.info("User created successfully. ID: {}, Email: {}", user.getId(), user.getEmail());
        return userMapper.toDto(user);
    }

    @Override
    public GetUserDto update(UUID userId, UserDto userDto) {
        log.info("Attempting to update user with ID: {}", userId);

        User existingUser = getUserEntity(userId);

        if (!existingUser.getEmail().equals(userDto.getEmail())) {
            validateEmailUniqueness(userDto.getEmail());
        }

        userMapper.updateEntity(userDto, existingUser);
        userRepo.save(existingUser);

        log.info("User updated successfully. ID: {}, Email: {}", userId, userDto.getEmail());
        return userMapper.toDto(existingUser);
    }

    @Override
    public void delete(UUID userId) {
        log.info("Attempting to delete user with ID: {}", userId);

        if (!userRepo.existsById(userId)) {
            log.error("Delete operation failed. User not found with ID: {}", userId);
            throw new AppException(
                    "User not found with ID: " + userId,
                    HttpStatus.NOT_FOUND
            );
        }

        userRepo.deleteById(userId);
        log.info("User deleted successfully. ID: {}", userId);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepo.existsByEmail(email)) {
            log.error("Operation failed. User with email '{}' already exists", email);
            throw new AppException(
                    String.format("User with email '%s' already exists", email),
                    HttpStatus.CONFLICT
            );
        }
    }
}
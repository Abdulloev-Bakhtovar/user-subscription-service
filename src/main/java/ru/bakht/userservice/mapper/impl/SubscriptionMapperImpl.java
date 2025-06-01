package ru.bakht.userservice.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.bakht.userservice.dto.GetSubscriptionDto;
import ru.bakht.userservice.dto.SubscriptionDto;
import ru.bakht.userservice.dto.TopSubscriptionDto;
import ru.bakht.userservice.entity.Subscription;
import ru.bakht.userservice.entity.User;
import ru.bakht.userservice.exception.AppException;
import ru.bakht.userservice.mapper.SubscriptionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SubscriptionMapperImpl implements SubscriptionMapper {

    @Override
    public Subscription toEntity(SubscriptionDto subscriptionDto, User user) {
        if (subscriptionDto == null || user == null) {
            log.error("Cannot map to Subscription entity: subscriptionDto or user is null");
            throw new AppException("SubscriptionDto and User must not be null", HttpStatus.BAD_REQUEST);
        }

        return Subscription.builder()
                .serviceName(subscriptionDto.getServiceName())
                .user(user)
                .build();
    }

    private GetSubscriptionDto toDto(Subscription subscription) {
        if (subscription == null) {
            log.error("Cannot map null Subscription to DTO");
            throw new AppException("Subscription must not be null", HttpStatus.BAD_REQUEST);
        }

        return GetSubscriptionDto.builder()
                .id(subscription.getId())
                .serviceName(subscription.getServiceName())
                .createdAt(subscription.getCreatedAt())
                .build();
    }

    @Override
    public List<GetSubscriptionDto> toDtoList(List<Subscription> subscriptions) {
        if (subscriptions == null || subscriptions.isEmpty()) {
            return new ArrayList<>();
        }

        return subscriptions.stream()
                .map(this::toDto)
                .toList();
    }

    public List<TopSubscriptionDto> toTopSubDtoList(List<Object[]> objects) {
        if (objects == null || objects.isEmpty()) {
            return new ArrayList<>();
        }

        return objects.stream()
                .map(this::toTopSubscriptionDto)
                .collect(Collectors.toList());
    }

    private TopSubscriptionDto toTopSubscriptionDto(Object[] object) {
        if (object == null || object.length < 2
                || !(object[0] instanceof String) || !(object[1] instanceof Long)
        ) {
            log.error("Invalid object array for TopSubscriptionDto mapping: {}", (Object) object);
            throw new AppException("Invalid data format for top subscription", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new TopSubscriptionDto(
                (String) object[0],
                (Long) object[1]
        );
    }
}

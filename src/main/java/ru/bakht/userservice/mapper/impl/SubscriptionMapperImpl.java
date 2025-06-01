package ru.bakht.userservice.mapper.impl;

import org.springframework.stereotype.Component;
import ru.bakht.userservice.dto.GetSubscriptionDto;
import ru.bakht.userservice.dto.SubscriptionDto;
import ru.bakht.userservice.dto.TopSubscriptionDto;
import ru.bakht.userservice.entity.Subscription;
import ru.bakht.userservice.entity.User;
import ru.bakht.userservice.mapper.SubscriptionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubscriptionMapperImpl implements SubscriptionMapper {

    @Override
    public Subscription toEntity(SubscriptionDto subscriptionDto, User user) {
        if (subscriptionDto == null) {
            return null;
        }

        return Subscription.builder()
                .serviceName(subscriptionDto.getServiceName())
                .user(user)
                .build();
    }

    private GetSubscriptionDto toDto(Subscription subscription) {
        if (subscription == null) {
            return null;
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
        return new TopSubscriptionDto(
                (String) object[0],
                (Long) object[1]
        );
    }
}

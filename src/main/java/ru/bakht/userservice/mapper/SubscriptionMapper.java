package ru.bakht.userservice.mapper;

import ru.bakht.userservice.dto.GetSubscriptionDto;
import ru.bakht.userservice.dto.SubscriptionDto;
import ru.bakht.userservice.dto.TopSubscriptionDto;
import ru.bakht.userservice.entity.Subscription;
import ru.bakht.userservice.entity.User;

import java.util.List;

public interface SubscriptionMapper {

    Subscription toEntity(SubscriptionDto subscriptionDto, User user);

    List<GetSubscriptionDto> toDtoList(List<Subscription> subscriptions);

    List<TopSubscriptionDto> toTopSubDtoList(List<Object[]> objects);
}

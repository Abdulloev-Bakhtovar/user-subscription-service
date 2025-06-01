package ru.bakht.userservice.service;

import ru.bakht.userservice.dto.GetSubscriptionDto;
import ru.bakht.userservice.dto.SubscriptionDto;
import ru.bakht.userservice.dto.TopSubscriptionDto;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    void addSubscription(UUID userId, SubscriptionDto subscriptionDto);

    List<GetSubscriptionDto> getUserSubscriptions(UUID userId);

    void deleteSubscription(UUID userId, UUID subId);

    List<TopSubscriptionDto> getTop3PopularSubscriptions();
}

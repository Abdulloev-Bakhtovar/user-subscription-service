package ru.bakht.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakht.userservice.dto.GetSubscriptionDto;
import ru.bakht.userservice.dto.SubscriptionDto;
import ru.bakht.userservice.dto.TopSubscriptionDto;
import ru.bakht.userservice.exception.AppException;
import ru.bakht.userservice.mapper.SubscriptionMapper;
import ru.bakht.userservice.repository.SubscriptionRepo;
import ru.bakht.userservice.service.SubscriptionService;
import ru.bakht.userservice.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepo subscriptionRepo;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public void addSubscription(UUID userId, SubscriptionDto subscriptionDto) {
        log.info("Attempting to add subscription for user ID: {}", userId);

        if (subscriptionRepo.existsByUser_IdAndServiceName(userId, subscriptionDto.getServiceName())) {
            String errorMessage = String.format(
                    "Subscription '%s' already exists for user ID: %s",
                    subscriptionDto.getServiceName(),
                    userId
            );
            log.error(errorMessage);
            throw new AppException(errorMessage, HttpStatus.CONFLICT);
        }

        var user = userService.getUserEntity(userId);
        var subscription = subscriptionMapper.toEntity(subscriptionDto, user);
        subscriptionRepo.save(subscription);

        log.info("Subscription added successfully for user ID: {}. Subscription details: {}",
                userId, subscriptionDto);
    }

    @Override
    public List<GetSubscriptionDto> getUserSubscriptions(UUID userId) {

        var user = userService.getUserEntity(userId);
        var subscriptions = subscriptionRepo.findByUser(user);

        return subscriptionMapper.toDtoList(subscriptions);
    }

    @Override
    public void deleteSubscription(UUID userId, UUID subId) {
        log.info("Attempting to delete subscription ID: {} for user ID: {}", subId, userId);

        var subscription = subscriptionRepo.findById(subId)
                .orElseThrow(() -> {
                    log.error("Subscription not found with ID: {}", subId);
                    return new AppException(
                            "Subscription not found with ID: " + subId,
                            HttpStatus.NOT_FOUND
                    );
                });

        if (!subscription.getUser().getId().equals(userId)) {
            log.error("Subscription ID: {} doesn't belong to user ID: {}", subId, userId);
            throw new AppException(
                    "Subscription not found for user with ID: " + userId,
                    HttpStatus.NOT_FOUND
            );
        }

        subscriptionRepo.delete(subscription);
        log.info("Subscription ID: {} deleted successfully for user ID: {}", subId, userId);
    }

    @Override
    public List<TopSubscriptionDto> getTop3PopularSubscriptions() {
        var objects = subscriptionRepo.findTop3PopularSubscriptions();
        return subscriptionMapper.toTopSubDtoList(objects);
    }
}
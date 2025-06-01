package ru.bakht.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bakht.userservice.dto.GetSubscriptionDto;
import ru.bakht.userservice.dto.SubscriptionDto;
import ru.bakht.userservice.dto.TopSubscriptionDto;
import ru.bakht.userservice.service.SubscriptionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public void addSubscription(@PathVariable("id") UUID userId,
                                @RequestBody @Valid SubscriptionDto subscription) {
        subscriptionService.addSubscription(userId, subscription);
    }

    @GetMapping("/users/{id}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public List<GetSubscriptionDto> getUserSubscriptions(@PathVariable("id") UUID userId) {
        return subscriptionService.getUserSubscriptions(userId);
    }

    @DeleteMapping("/users/{id}/subscriptions/{sub_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(
            @PathVariable("id") UUID userId,
            @PathVariable("sub_id") UUID subId) {
        subscriptionService.deleteSubscription(userId, subId);
    }

    @GetMapping("/subscriptions/top")
    @ResponseStatus(HttpStatus.OK)
    public List<TopSubscriptionDto> getTop3PopularSubscriptions() {
        return subscriptionService.getTop3PopularSubscriptions();
    }
}

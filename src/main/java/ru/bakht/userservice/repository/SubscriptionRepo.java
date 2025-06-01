package ru.bakht.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.bakht.userservice.entity.Subscription;
import ru.bakht.userservice.entity.User;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepo extends JpaRepository<Subscription, UUID> {

    List<Subscription> findByUser(User user);

    @Query("SELECT s.serviceName, COUNT(s) as count " +
            "FROM Subscription s " +
            "GROUP BY s.serviceName " +
            "ORDER BY count DESC " +
            "LIMIT 3")
    List<Object[]> findTop3PopularSubscriptions();

    boolean existsByUser_IdAndServiceName(UUID userId, String serviceName);
}

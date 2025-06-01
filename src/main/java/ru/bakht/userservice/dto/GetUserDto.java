package ru.bakht.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserDto extends BaseDto {

    Instant updatedAt;
    String name;
    String email;

    @JsonProperty("subscriptions")
    List<GetSubscriptionDto> subscriptionDtos;
}

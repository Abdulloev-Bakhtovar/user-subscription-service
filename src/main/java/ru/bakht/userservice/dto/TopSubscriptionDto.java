package ru.bakht.userservice.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopSubscriptionDto {

    String serviceName;
    Long subscribersCount;
}

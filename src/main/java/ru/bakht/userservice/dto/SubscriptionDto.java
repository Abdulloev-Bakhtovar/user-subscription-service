package ru.bakht.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionDto {

    @NotBlank(message = "Service name cannot be blank")
    @Size(min = 2, max = 255, message = "Service name must be between 2 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Service name can only contain letters, numbers and spaces")
    String serviceName;
}

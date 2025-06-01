package ru.bakht.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must be less than 255 characters")
    String email;
}

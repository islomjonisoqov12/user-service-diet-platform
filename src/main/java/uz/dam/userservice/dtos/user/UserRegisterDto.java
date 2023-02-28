package uz.dam.userservice.dtos.user;

import lombok.*;
import uz.dam.userservice.entities.enums.ApplicationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegisterDto {
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "cannot be blank")
    private String firstName;
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "cannot be blank")
    private String email;
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "cannot be blank")
    private String password;
    @NotNull(message = "cannot be null")
    private ApplicationType type;

}

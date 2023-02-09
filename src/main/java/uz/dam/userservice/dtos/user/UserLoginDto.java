package uz.dam.userservice.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.dam.userservice.entities.enums.ApplicationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "cannot be blank")
    private String email;
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "cannot be blank")
    private String password;

    private ApplicationType type;
}

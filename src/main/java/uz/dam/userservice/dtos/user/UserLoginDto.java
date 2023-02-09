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
public class UserLoginDto {

    private String email;
    private String password;
    private ApplicationType type;
}

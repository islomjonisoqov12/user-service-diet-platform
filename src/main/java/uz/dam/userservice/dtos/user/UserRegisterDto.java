package uz.dam.userservice.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.dam.userservice.entities.enums.ApplicationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String firstName;
    private String email;
    private String password;
    private ApplicationType type;

}

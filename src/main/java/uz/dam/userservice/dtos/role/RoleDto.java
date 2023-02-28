package uz.dam.userservice.dtos.role;

import java.util.List;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDto {

    Long id;
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    @NotBlank(message = "cannot be blank")
    String name;
    @NotEmpty(message = "cannot be empty")
    List<Long> authorityList;
}

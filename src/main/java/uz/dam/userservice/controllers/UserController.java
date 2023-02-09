package uz.dam.userservice.controllers;

import lombok.*;
import org.springframework.graphql.data.method.annotation.Argument;
import static uz.dam.userservice.config.Constants.*;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.dam.userservice.dtos.user.JWTokenDto;
import uz.dam.userservice.dtos.user.UserLoginDto;
import uz.dam.userservice.dtos.user.UserRegisterDto;
import uz.dam.userservice.entities.User;
import uz.dam.userservice.services.UserService;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Controller
public class UserController {

    private final UserService service;
    @PostMapping("/auth/login")
    @ResponseBody
    public HttpEntity<JWTokenDto> login(@RequestBody UserLoginDto loginDto) {
        return service.login(loginDto);
    }

    @PutMapping("/auth/refresh-token")
    @ResponseBody
    public HttpEntity<JWTokenDto> refreshToken(@RequestParam String refreshToken) {
        return service.refreshToken(refreshToken);
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public HttpEntity<JWTokenDto> register(@RequestBody UserRegisterDto registerDto){
        return service.selfRegister(registerDto);
    }


    @SchemaMapping(typeName = "Query", field = "userById")
//    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public Optional<User> getUserById(@Argument Long id){
        return service.getUser(id);
    }

    @SchemaMapping(typeName = "Query", field = "users")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public List<User> getUsers(){
        return service.getUsers();
    }


}

package uz.dam.userservice.controllers;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import static uz.dam.userservice.config.Constants.*;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
@Slf4j
//@CrossOrigin(origins = {"https://studio.apollographql.com/"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
public class UserController {

    private final UserService service;
    @MutationMapping(name = "login")
    public JWTokenDto login(@Argument UserLoginDto dto) {
        log.info("User Controller login; UserLoginDto {}", dto);
        return service.login(dto);
    }

    @MutationMapping(name = "refreshToken")
    public JWTokenDto refreshToken(@Argument String refreshToken) {
        log.info("User Controller refreshToken; RefreshToken: {}", refreshToken);
        return service.refreshToken(refreshToken);
    }

    @MutationMapping(name = "selfRegister")
    public JWTokenDto selfRegister(@Argument UserRegisterDto dto){
        log.info("User Controller selfRegister; UserRegisterDto: {}", dto);
        return service.selfRegister(dto);
    }


    @SchemaMapping(typeName = "Query", field = "userById")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public Optional<User> getUserById(@Argument Long id){
        return service.getUser(id);
    }

    @SchemaMapping(typeName = "Query", field = "users")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public List<User> getUsers(){
        return service.getUsers();
    }


}

package uz.dam.userservice.controllers;

import graphql.GraphQLContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.dam.userservice.config.Translator;
import uz.dam.userservice.criteria.Criteria;
import uz.dam.userservice.criteria.UserCriteria;
import uz.dam.userservice.dtos.user.JWTokenDto;
import uz.dam.userservice.dtos.user.UserLoginDto;
import uz.dam.userservice.dtos.user.UserRegisterDto;
import uz.dam.userservice.entities.Role;
import uz.dam.userservice.entities.User;
import uz.dam.userservice.exceptions.BadRequestException;
import uz.dam.userservice.exceptions.enums.ExceptionType;
import uz.dam.userservice.services.UserService;

import static uz.dam.userservice.config.Constants.*;
import static uz.dam.userservice.utils.FunctionUtils.*;
import static uz.dam.userservice.utils.ConsoleColors.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static uz.dam.userservice.config.Constants.HAS_ROLE_SUPER_ADMIN;
import static uz.dam.userservice.config.EntityNameConstants.REFRESH_TOKEN;

@Getter
@Setter
@RequiredArgsConstructor
@Controller
@Slf4j
//@CrossOrigin(origins = {"https://studio.apollographql.com/"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
public class UserController {

    private final UserService service;
    @MutationMapping(name = "login")
    public JWTokenDto login(@Argument UserLoginDto input) {
        if(true){
            throw new BadRequestException(Translator.getMessage("refresh.not.found"), ExceptionType.OBJECT_NOT_FOUND, REFRESH_TOKEN);
        }
        log.info("User Controller login; UserLoginDto {}", input);
        return service.login(input);
    }

    @MutationMapping(name = "refreshToken")
    public JWTokenDto refreshToken(@Argument String refreshToken) {
        log.info("User Controller refreshToken; RefreshToken: {}", refreshToken);
        return service.refreshToken(refreshToken);
    }

    @MutationMapping(name = "selfRegister")
    public JWTokenDto selfRegister(@Argument @Valid UserRegisterDto input){
        printColored.accept("User Controller selfRegister; UserRegisterDto: "+ input.toString(), RED_BOLD);
        return service.selfRegister(input);
    }


    @SchemaMapping(typeName = "Query", field = "userById")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public Optional<User> getUserById(@Argument Long id){
        return service.getUser(id);
    }

    @SchemaMapping(typeName = "Query", field = "users")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public Page<User> getAllUsers(@Argument UserCriteria input){
        print.accept("User Controller get All Users for SuperAdmin page; UserCriteria:", input);
        return service.getUsers(input);
    }

    @SchemaMapping(typeName = "Query", field = "userByEmail")
    @PreAuthorize(HAS_ROLE_MERCHANT_OR_OWNER)
    public Optional<User> getUserByEmail(@Argument String email){
        print.accept("User Controller getUserByEmail for MerchantPage; email:", email);
        return service.getUserByEmail(email);
    }



}

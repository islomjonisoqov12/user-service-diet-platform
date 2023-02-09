package uz.dam.userservice.controllers;

import lombok.*;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.dam.userservice.dtos.user.JWTokenDto;
import uz.dam.userservice.dtos.user.UserLoginDto;
import uz.dam.userservice.dtos.user.UserRegisterDto;
import uz.dam.userservice.services.UserService;

@Getter
@Setter
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService service;
    @PostMapping("/auth/login")
    public HttpEntity<JWTokenDto> login(@RequestBody UserLoginDto loginDto) {
        return service.login(loginDto);
    }

    @PutMapping("/auth/refresh-token")
    public HttpEntity<JWTokenDto> refreshToken(@RequestParam String refreshToken) {
        return service.refreshToken(refreshToken);
    }

    @PostMapping("/auth/register")
    public HttpEntity<JWTokenDto> register(@RequestBody UserRegisterDto registerDto){
        return service.selfRegister(registerDto);
    }


}

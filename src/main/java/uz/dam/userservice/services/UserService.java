package uz.dam.userservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.dam.userservice.dtos.DeveloperMessage;
import uz.dam.userservice.dtos.user.JWTokenDto;
import uz.dam.userservice.dtos.user.UserLoginDto;
import uz.dam.userservice.dtos.user.UserRegisterDto;
import uz.dam.userservice.entities.RefreshToken;
import uz.dam.userservice.entities.User;
import uz.dam.userservice.entities.enums.UserStatus;
import uz.dam.userservice.exceptions.BadRequestException;
import uz.dam.userservice.exceptions.enums.ExceptionType;
import uz.dam.userservice.repositories.RefreshTokenRepository;
import uz.dam.userservice.repositories.RoleRepository;
import uz.dam.userservice.repositories.UserRepository;
import uz.dam.userservice.security.jwt.JwtProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static uz.dam.userservice.config.EntityNameConstants.REFRESH_TOKEN;

@Service
@Slf4j
public class UserService {
    private final RoleRepository roleRepository;
    @Value("${jwt.access.expire}")
    private Long jwtAccessExpire;
    @Value("${jwt.refresh.expire}")
    private Long jwtRefreshExpire;


    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository repository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtProvider jwtProvider,
                       RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.repository = repository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * user login
     * @param loginDto - param for login
     * @return jwt token with refresh token
     */
    public HttpEntity<JWTokenDto> login(UserLoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessJwt = jwtProvider.generateAccessToken(authentication);
        String refreshJwt = jwtProvider.generateRefreshToken(authentication, loginDto.getType());
        JWTokenDto jwTokenDto = new JWTokenDto(accessJwt, refreshJwt, new Date(System.currentTimeMillis() + jwtAccessExpire), new Date(System.currentTimeMillis() + jwtRefreshExpire));
        return ResponseEntity.ok(jwTokenDto);
    }

    /**
     * get new access and refresh token for user
     * @param oldRefreshToken given old refresh token
     * @return new access token and new refresh token
     */
    public HttpEntity<JWTokenDto> refreshToken(String oldRefreshToken) {
        boolean validateRefreshToken = jwtProvider.validateRefreshToken(oldRefreshToken);
        if (validateRefreshToken) {
            Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(oldRefreshToken);
            if (optionalRefreshToken.isEmpty()) {
                DeveloperMessage developerMessage = new DeveloperMessage();
                developerMessage.setUz("refresh token topilmadi berilgan  token bilan");
                developerMessage.setEn("refresh token not found with given token");
                developerMessage.setRu("токен обновления не найден с данным токеном");
                throw new BadRequestException(developerMessage, ExceptionType.OBJECT_NOT_FOUND, REFRESH_TOKEN);
            }
            Authentication authentication = jwtProvider.getAuthentication(oldRefreshToken);
            String accessJwt = jwtProvider.generateAccessToken(authentication);
            String refreshJwt = jwtProvider.generateRefreshToken(authentication, optionalRefreshToken.get().getType());
            JWTokenDto jwTokenDto = new JWTokenDto(accessJwt, refreshJwt, new Date(System.currentTimeMillis() + jwtAccessExpire), new Date(System.currentTimeMillis() + jwtRefreshExpire));
            return ResponseEntity.ok(jwTokenDto);
        }
        throw new BadRequestException(new DeveloperMessage("token yaroqsiz", "токен недействителен", "token is invalid"), ExceptionType.JWT_INVALID, REFRESH_TOKEN);
    }

    public HttpEntity<JWTokenDto> selfRegister(UserRegisterDto registerDto) {

        User user = User.builder()
                .firstName(registerDto.getFirstName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .status(UserStatus.INACTIVE)
                .role(roleRepository.findById(1L).get())
                .build();
        repository.save(user);
        return login(new UserLoginDto(registerDto.getEmail(), registerDto.getPassword(), registerDto.getType()));
    }

    public Optional<User> getUser(Long id) {
        return repository.findById(id);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }
}

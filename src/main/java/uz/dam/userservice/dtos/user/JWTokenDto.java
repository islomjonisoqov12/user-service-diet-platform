package uz.dam.userservice.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JWTokenDto {

    private String accessToken;
    private String refreshToken;
    private Date expireAccess;
    private Date expireRefresh;
}

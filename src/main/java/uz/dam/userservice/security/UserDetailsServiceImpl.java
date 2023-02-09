package uz.dam.userservice.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.dam.userservice.dtos.DeveloperMessage;
import uz.dam.userservice.entities.Authority;
import uz.dam.userservice.entities.User;
import uz.dam.userservice.entities.enums.UserStatus;
import uz.dam.userservice.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Authenticating {}", email);
        return userRepository
                .findByPhoneNumberWithRole(email)
                .map(user -> createSpringSecurityUser(email, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        // user not active status
//        if (user.getStatus().equals(UserStatus.INACTIVE)) {
//            throw new UserNotActivatedException(DeveloperMessage.builder()
//                    .uz("foydalanuvchi akkounti faol emas iltimos avval akkaunt ni faollashtiring")
//                    .ru("учетная запись пользователя не активна, пожалуйста, сначала активируйте свою учетную запись")
//                    .en("User account is not active, please activate your account first")
//                    .build()
//            );
//        }
        // user ban status
        if(user.getStatus().equals(UserStatus.BAN)){
            throw new UserNotActivatedException(DeveloperMessage.builder()
                    .uz("Foydalanuvchi bloklangan")
                    .ru("Пользователь заблокирован")
                    .en("User is blocked")
                    .build());
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}

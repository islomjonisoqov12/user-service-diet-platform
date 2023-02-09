package uz.dam.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.dam.userservice.entities.RefreshToken;
import uz.dam.userservice.entities.enums.ApplicationType;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    @Modifying
    void removeByTypeAndCreatedBy(ApplicationType type, String createdBy);

    Optional<RefreshToken> findByTypeAndCreatedBy(ApplicationType type, String createdBy);

    Optional<RefreshToken> findByRefreshToken(String oldRefreshToken);

}
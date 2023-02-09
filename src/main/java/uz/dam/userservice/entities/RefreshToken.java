package uz.dam.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.dam.userservice.entities.enums.ApplicationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "refresh_tokens")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"created_by", "type"}))
public class RefreshToken extends AbstractAuditingEntity{
    @Column(unique = true, nullable = false)
    private String refreshToken;
    @Column(nullable = false)
    private ApplicationType type;
}

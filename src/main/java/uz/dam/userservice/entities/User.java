package uz.dam.userservice.entities;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.dam.userservice.entities.enums.Gender;
import uz.dam.userservice.entities.enums.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
@SQLDelete(sql = "update users set deleted = true where id = ?1")
@Where(clause = "deleted = false")
public class User extends AbstractAuditingEntity{

    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private Gender gender;
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    // get user authority method:  if user has role super admin owner or admin then user has no authorities
    public List<Authority> getAuthorities() {
        if (!role.getAuthorities().isEmpty()) {
            return role.getAuthorities();
        }
        // add user role as authority
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(role.getName()));
        return authorities;
    }
}

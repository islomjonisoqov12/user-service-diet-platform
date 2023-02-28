package uz.dam.userservice.criteria;

import lombok.*;
import uz.dam.userservice.entities.enums.Gender;
import uz.dam.userservice.entities.enums.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCriteria extends Criteria{
    private Long roleId;
    private Gender gender;
    private LocalDateTime startCreatedDate;
    private LocalDateTime endCreatedDate;
    private UserStatus status;
    private String createdBy;

    @Override
    public String toString() {
        return "UserCriteria{" +
                "roleId=" + roleId +
                ", gender=" + gender +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", search='" + search + '\'' +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}

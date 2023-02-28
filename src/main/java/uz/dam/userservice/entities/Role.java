package uz.dam.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
@SQLDelete(sql = "update roles set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Role extends AbstractAuditingEntity{
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Authority> authorities = new ArrayList<>();

}

package uz.dam.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "merchants")
public class Merchant extends AbstractAuditingEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String legalName;
    @Column(nullable = false)
    private String tin;
    @Column(nullable = false)
    private String tel;
    @OneToOne(optional = false)
    private User owner;
    @OneToMany
    private List<User> employees;
    private String address;
}

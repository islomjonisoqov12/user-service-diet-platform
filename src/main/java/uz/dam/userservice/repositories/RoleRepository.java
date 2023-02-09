package uz.dam.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dam.userservice.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
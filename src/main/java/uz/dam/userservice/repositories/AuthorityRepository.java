package uz.dam.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dam.userservice.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
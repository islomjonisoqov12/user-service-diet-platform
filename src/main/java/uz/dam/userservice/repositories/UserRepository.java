package uz.dam.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.dam.userservice.entities.Role;
import uz.dam.userservice.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "from users u join u.role where u.phoneNumber = :phoneNumber or u.email = :phoneNumber")
    Optional<User> findByPhoneNumberWithRole(String phoneNumber);
}

package uz.dam.userservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.dam.userservice.entities.User;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "from users u join u.role where u.phoneNumber = :phoneNumber or u.email = :phoneNumber")
    Optional<User> findByPhoneNumberWithRole(String phoneNumber);

    @Query(value = "select * from get_all_users(i_search => :search, i_role_id => :roleId, i_gender => :gender, i_start_created_date => :startCreatedDate, i_end_created_date => :endCreatedDate, i_status => :status, i_created_by => :createdBy)", nativeQuery = true)
    Page<User> findAllUsers(Long roleId, String gender, LocalDateTime startCreatedDate, LocalDateTime endCreatedDate, String status, String createdBy, String search, Pageable pageable);

    Optional<User> findByEmail(String email);

}

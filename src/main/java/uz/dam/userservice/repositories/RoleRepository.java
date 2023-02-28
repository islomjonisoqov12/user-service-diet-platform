package uz.dam.userservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.dam.userservice.entities.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true, value = "select * from roles where name ilike concat('%', :search, '%')")
    Page<Role> getRolesForAdmin(String search, Pageable pageable);
    @Query(nativeQuery = true,
            value = "with c_merchant as (select m.*, u.email from merchants m\n" +
            "                             join users u on u.id = m.owner_id\n" +
            "                             where :name = u.email or\n" +
            "                             :name in (select email from users\n" +
            "                                                        join merchants_employees me on users.id = me.employees_id\n" +
            "                                                        where me.merchants_id = m.id))\n" +
            "select r.*\n" +
            "from roles r\n" +
            "        join c_merchant cm on r.created_by = cm.email")
    List<Role> getRolesForOwner(String name);
}
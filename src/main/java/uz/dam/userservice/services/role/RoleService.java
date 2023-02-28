package uz.dam.userservice.services.role;

import org.springframework.data.domain.Page;
import uz.dam.userservice.criteria.Criteria;
import uz.dam.userservice.dtos.role.RoleDto;
import uz.dam.userservice.entities.Authority;
import uz.dam.userservice.entities.Role;
import uz.dam.userservice.services.base.GenericCrudService;

import java.util.List;
import java.util.Optional;

public interface RoleService extends GenericCrudService<Role, RoleDto, RoleDto, Long, Criteria> {

    Page<Role> getRolesForAdmin(Criteria input);

    List<Role> getRolesForOwner(String name);

    Optional<Authority> getAuthorityById(Long id);

    List<Authority> getAuthorities();

}

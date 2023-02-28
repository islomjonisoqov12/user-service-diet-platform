package uz.dam.userservice.controllers;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import uz.dam.userservice.config.Translator;
import uz.dam.userservice.criteria.Criteria;
import uz.dam.userservice.dtos.role.RoleDto;
import uz.dam.userservice.entities.Authority;
import uz.dam.userservice.entities.Role;
import uz.dam.userservice.services.role.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static uz.dam.userservice.config.Constants.HAS_ROLE_SUPER_ADMIN;
import static uz.dam.userservice.security.AuthoritiesConstants.*;
import static uz.dam.userservice.utils.FunctionUtils.*;

@Controller
public class RoleController extends AbstractController<RoleService>{


    protected RoleController(RoleService service) {
        super(service);
    }

    /**
     * @Success get list role with a filtered param access only super admin
     * @param input criteria for filter and search
     * @return list role
     */
    @SchemaMapping(typeName = "Query", field = "roles")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public Page<Role> getRolesForAdmin(@Argument Criteria input){
        print.accept("Role Controller getRoles for Super Admin; Criteria:", input);
        return service.getRolesForAdmin(input);
    }
    @SchemaMapping(typeName = "Query", field = "rolesOwner")
    @PreAuthorize(HAS_ROLE_SUPER_ADMIN)
    public List<Role> getRoleForOwner(Authentication authentication){
        print.accept("Role Controller getRoleForOwner for Super Admin", "method");
        return service.getRolesForOwner(authentication.getName());
    }

    /**
     * @throw exception &code 200 bad request if authority list is empty
     * @Success $code 200 success if successfully created new role
     * @param input role dto field for create new role
     * @return id - role id Long
     */
    @SchemaMapping(typeName = "Mutation", field = "createRole")
    @Secured(value = {SUPER_ADMIN, ADMIN, OWNER})
    public Long create(@Argument @Valid RoleDto input){
        print.accept("Role Controller create role; RoleDto:", input);
        return service.create(input);
    }

    /**
     * @throws uz.dam.userservice.exceptions.BadRequestException if authority list is empty
     * @throws uz.dam.userservice.exceptions.ResourceNotFoundException if role not found with given id
     * @param input  role dto for update created role
     * @return string "success"
     */
    @SchemaMapping(typeName = "Mutation", field = "updateRole")
    @Secured(value = {SUPER_ADMIN,ADMIN, OWNER})
    public String update(@Argument RoleDto input){
        print.accept("Role Controller update role; RoleDto:", input);
        service.update(input);
        return Translator.getMessage("success.updated");
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteRole")
    @Secured(value = {SUPER_ADMIN, OWNER})
    public String delete(@Argument Long id){
        print.accept("Role Controller delete role; ID:", id);
        service.delete(id);
        return Translator.getMessage("success.deleted");
    }

    @SchemaMapping(typeName = "Query", field = "authorityById")
    @Secured(value = {SUPER_ADMIN, OWNER})
    public Optional<Authority> getAuthorityById(@Argument Long id){
        print.accept("Role Controller get authority by id; ID:", id);
        return service.getAuthorityById(id);
    }

    @SchemaMapping(typeName = "Query", field = "authorities")
    @Secured(value = {SUPER_ADMIN, OWNER})
    public List<Authority> getAllAuthorities(){
        print.accept("Role Controller get all authorities;", "method");
        return service.getAuthorities();
    }







}

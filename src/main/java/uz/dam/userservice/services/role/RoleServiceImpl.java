package uz.dam.userservice.services.role;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.dam.userservice.config.Translator;
import uz.dam.userservice.criteria.Criteria;
import uz.dam.userservice.dtos.role.RoleDto;
import uz.dam.userservice.entities.Authority;
import uz.dam.userservice.entities.Role;
import uz.dam.userservice.exceptions.BadRequestException;
import uz.dam.userservice.exceptions.ResourceNotFoundException;
import uz.dam.userservice.exceptions.enums.ExceptionType;
import uz.dam.userservice.mappers.RoleMapper;
import uz.dam.userservice.repositories.AuthorityRepository;
import uz.dam.userservice.repositories.RoleRepository;
import java.util.List;
import java.util.Optional;

import static uz.dam.userservice.utils.FunctionUtils.*;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final AuthorityRepository authorityRepository;
    private final RoleMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository,
                           AuthorityRepository authorityRepository, RoleMapper mapper) {
        this.repository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<Role> getRolesForAdmin(Criteria input) {
        return repository.getRolesForAdmin(input.getSearch() == null ? "%" : input.getSearch(), input.getPageable());
    }

    @Override
    public List<Role> getRolesForOwner(String name) {
        return repository.getRolesForOwner(name);
    }

    @Override
    public Long create(RoleDto dto) {
        List<Authority> allById = authorityRepository.findAllById(dto.getAuthorityList());
        if (!hasRolePrefix.test(dto.getName())) {
            dto.setName("ROLE_" + dto.getName().toUpperCase());
        }
        if (allById.isEmpty()) {
            throw new BadRequestException(Translator.getMessage("list.empty"), ExceptionType.AUTHORITY_LIST_EMPTY, "Role");
        }
        Role role = new Role(dto.getName(), allById);
        repository.save(role);
        return role.getId();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(RoleDto dto) {
        if (!hasRolePrefix.test(dto.getName())) {
            dto.setName("ROLE_" + dto.getName().toUpperCase());
        }
        Role role1 = repository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(Translator.getMessage("obj.not.found"), "Role", "NOT_FOUND"));
        role1 = mapper.fromUpdateDto(dto, role1);
        if (dto.getAuthorityList()!=null && !dto.getAuthorityList().isEmpty()) {
            List<Authority> allById = authorityRepository.findAllById(dto.getAuthorityList());
            if (allById.isEmpty()) {
                throw new BadRequestException(Translator.getMessage("list.empty"), ExceptionType.AUTHORITY_LIST_EMPTY, "Role");
            }
            role1.setAuthorities(allById);
        }
        repository.save(role1);
    }

    @Override
    public Role get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<Role> getAll(Criteria criteria) {
        return null;
    }

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }

    @Override
    public List<Authority> getAuthorities() {
        return authorityRepository.findAll();
    }
}

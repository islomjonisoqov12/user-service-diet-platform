package uz.dam.userservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.dam.userservice.dtos.role.RoleDto;
import uz.dam.userservice.entities.Role;
import uz.dam.userservice.mappers.base.AbstractMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper extends AbstractMapper<Role, RoleDto> {
    @Override
    Role fromUpdateDto(RoleDto updateDto,@MappingTarget  Role entity);
}

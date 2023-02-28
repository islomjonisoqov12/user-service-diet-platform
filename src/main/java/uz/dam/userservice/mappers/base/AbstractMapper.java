package uz.dam.userservice.mappers.base;


import org.mapstruct.MappingTarget;
import uz.dam.userservice.entities.BaseGenericAuditingEntity;

import java.util.List;

public interface AbstractMapper<
        E extends BaseGenericAuditingEntity,
        D
        > extends BaseGenericMapper {
    D toDto(E entity);
    E fromUpdateDto(@MappingTarget D updateDto, E entity);
}

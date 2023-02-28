package uz.dam.userservice.services.base;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.dam.userservice.mappers.base.BaseGenericMapper;

public abstract class AbstractService<
        R extends JpaRepository,
        M extends BaseGenericMapper
        > implements BaseGenericService {

    protected final M mapper;
    protected final R repository;

    protected AbstractService(M mapper, R repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

}

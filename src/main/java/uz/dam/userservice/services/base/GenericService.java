package uz.dam.userservice.services.base;


import org.springframework.data.domain.Page;
import uz.dam.userservice.criteria.base.BaseGenericCriteria;

import java.io.Serializable;
import java.util.List;

public interface GenericService<
        D ,
        K extends Serializable,
        C extends BaseGenericCriteria
        > extends BaseGenericService {

    D get(K id);

    Page<D> getAll(C criteria);

}

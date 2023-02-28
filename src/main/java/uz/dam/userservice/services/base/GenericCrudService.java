package uz.dam.userservice.services.base;

import uz.dam.userservice.criteria.base.BaseGenericCriteria;
import java.io.Serializable;

public interface GenericCrudService<
        D ,
        CD ,
        UD ,
        K extends Serializable,
        C extends BaseGenericCriteria
        > extends GenericService<D, K, C> {
    K create(CD dto);

    void delete(K id);

    void update(UD dto);
}

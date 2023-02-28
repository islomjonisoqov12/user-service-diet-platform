package uz.dam.userservice.services.merchant;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.dam.userservice.criteria.base.BaseGenericCriteria;

import java.io.Serializable;
import java.util.List;
@Service
public class MerchantServiceImpl implements MerchantService {
    @Override
    public Serializable create(Object dto) {
        return null;
    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void update(Object dto) {

    }

    @Override
    public Object get(Serializable id) {
        return null;
    }

    @Override
    public Page getAll(BaseGenericCriteria criteria) {
        return null;
    }
}

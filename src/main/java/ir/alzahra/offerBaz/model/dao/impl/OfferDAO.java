package ir.alzahra.offerBaz.model.dao.impl;

import ir.alzahra.offerBaz.model.dao.IOfferDAO;
import ir.alzahra.offerBaz.model.entity.BankEntity;
import ir.alzahra.offerBaz.model.entity.OfferRequestEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: zahra soltaninejad
 * @Date: 5/3/2019
 **/
@Repository
public class OfferDAO extends AbstractDAO implements IOfferDAO{

    @Override
    public void insert(OfferRequestEntity offerRequestEntity) {
        entityManager.persist(offerRequestEntity);
    }

    @Override
    public void update(OfferRequestEntity entity) {

    }

    @Override
    public void delete(OfferRequestEntity entity) {

    }

}

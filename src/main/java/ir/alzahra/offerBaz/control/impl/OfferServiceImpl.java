package ir.alzahra.offerBaz.control.impl;


import ir.alzahra.offerBaz.control.IOfferCheckService;
import ir.alzahra.offerBaz.control.IOfferService;
import ir.alzahra.offerBaz.exception.BaseException;
import ir.alzahra.offerBaz.model.dao.IBankDao;
import ir.alzahra.offerBaz.model.dao.IOfferDAO;
import ir.alzahra.offerBaz.model.dao.IProductDao;
import ir.alzahra.offerBaz.model.entity.BankEntity;
import ir.alzahra.offerBaz.model.entity.OfferEntity;
import ir.alzahra.offerBaz.model.entity.OfferRequestEntity;
import ir.alzahra.offerBaz.model.entity.ProductEntity;
import ir.alzahra.offerBaz.notify.CustomSpringEvent;
import ir.alzahra.offerBaz.notify.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Mobina Pak
 * @Date: 5/3/2019
 **/
@Service
public class OfferServiceImpl implements IOfferService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IBankDao bankDao;

    @Autowired
    private CustomSpringEvent applicationEventPublisher;

    @Autowired
    private IOfferCheckService offerCheckService;

    @Autowired
    private IOfferDAO offerDao;

    private String finalcode;


    public String generateUniqueCode(String name2) throws BaseException {
       long code = productDao.getCountOfRecord()+1000;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDateTime now = LocalDateTime.now();
        finalcode =dtf.format(now)+name2+code;
       return finalcode;
    }

//    public void initializeDatabase(String name) throws BaseException {
//        productDao.initializeDatabase(name);
//
//    }

    @Override
    public void insertBank(BankEntity bank) throws BaseException {
        offerCheckService.checkInsertBank(bank);
       BankEntity duplicateBank= bankDao.findByName(bank.getName());
       if (Objects.nonNull(duplicateBank))
           throw new BaseException("bank.insert.duplicateName");
       if (Objects.nonNull(bankDao.findBankByAbbreviation(bank.getNameAbbreviation())))
           throw new BaseException("bank.insert.duplicateMokhafafName");
        bankDao.insert(bank);
        applicationEventPublisher.notify("bank.insert.success", NotificationType.Info);

    }

    @Override
    public List<BankEntity> getAllBanks() throws BaseException {
        List<BankEntity> banks=bankDao.getAllBanks();
        if (Objects.isNull(banks)|| banks.size()==0)
            throw new BaseException("bank.find.emptyBankList");
       return banks;
    }

    @Override
    public void updateBank(BankEntity bankEntity,String trackCode) throws BaseException {
        bankDao.update(bankEntity);
        applicationEventPublisher.notify("product.insert.success",new Object[]{trackCode}, NotificationType.Info);

    }

    @Override
    public BankEntity findBankByName(String bankName) throws BaseException {
        BankEntity bank=bankDao.findByName(bankName);
        if(Objects.isNull(bank))
            throw new BaseException("bank.find.notFoundBank");
        else
       return bank;
    }

    @Override
    public ProductEntity findProductByCode(String trackingCode) throws BaseException {
        ProductEntity p=productDao.findProductByCode(trackingCode);
        if (Objects.isNull(p))
            throw new BaseException("product.insert.notFoundProduct");
        else return p;
    }

    @Override
    public void updateProduct(ProductEntity productEntity) throws BaseException {
        if (Objects.isNull(productEntity.getId()))
            throw new BaseException("product.edit.notFoundForEdit");
        else{
            offerCheckService.checkUpdateProduct(productEntity);
           boolean isDuplicate;
           isDuplicate=productDao.duplicateName(productEntity);
           if (isDuplicate)
               throw new BaseException("product.insert.exist");
        productDao.update(productEntity);
        applicationEventPublisher.notify("product.update.success", NotificationType.Info);
        }
    }

    @Override
    public void deleteProduct(ProductEntity productEntity) throws BaseException {
        if (Objects.isNull(productEntity.getId()))
            throw new BaseException("product.delete.notFoundForDelete");
        else {
            productDao.delete(productEntity);
            applicationEventPublisher.notify("product.delete.success", NotificationType.Info);
        }
    }

    @Override
    public String findBankByAbbreviation(String name) throws BaseException {
        return bankDao.findBankByAbbreviation(name);
    }

    @Override
    public List<ProductEntity> searchProductByParam(Long param) throws BaseException {
        return productDao.searchProductByParam(param);

    }

    @Override
    public List<BankEntity> searchBankByParam(String bankName) throws BaseException {
        List<BankEntity> bankEntity=bankDao.findBankByParam(bankName);
        if(Objects.isNull(bankEntity) || bankEntity.size()==0)
            throw new BaseException("bank.find.notFoundBank");
        else return bankEntity;
    }

    @Override
    public void checkExistProduct(String name) throws BaseException {
        ProductEntity p =productDao.findByName(name);
        if (Objects.nonNull(p))
            throw new BaseException("product.insert.exist");
    }

    @Override
    public void deleteBank(BankEntity bankEntity) throws BaseException {
        bankDao.delete(bankEntity);
        applicationEventPublisher.notify("bank.delete.success", NotificationType.Info);

    }

    @Override
    public void editBankInfo(BankEntity bankEntity) throws BaseException {
        bankDao.update(bankEntity);
        applicationEventPublisher.notify("bank.edit.success", NotificationType.Info);

    }

    @Override
    public OfferRequestEntity insetOfferRequest(OfferRequestEntity offerRequestEntity) throws BaseException {

        offerCheckService.checkRequest(offerRequestEntity);
        offerRequestEntity.setOffers(findOffers(offerRequestEntity));
        offerDao.insert(offerRequestEntity);
        applicationEventPublisher.notify("request.insert.success", NotificationType.Info);
        return offerRequestEntity;

    }

    private List<OfferEntity> findOffers(OfferRequestEntity offerRequestEntity)throws BaseException {
        List<OfferEntity> offers = new ArrayList<>();
        //TODO
        List<ProductEntity> products = productDao.getAllProduct();
        if (products.size()==0)
            throw new BaseException("product.find.empty");
        int counter = 1;
        for (ProductEntity p:products
             ) {
           OfferEntity o= new OfferEntity();
           o.setName("Offer"+counter);
           o.setDescription("offerDescription"+1);
           o.setProduct(p);
            offers.add(o);
            counter++;
        }
        return offers;
    }
}

package ir.alzahra.offerBaz.view.beans.product;

import ir.alzahra.offerBaz.dto.BankDTO;
import ir.alzahra.offerBaz.dto.ProductDTO;
import ir.alzahra.offerBaz.enums.BorrowType;
import ir.alzahra.offerBaz.enums.DtoState;
import ir.alzahra.offerBaz.enums.FundType;
import ir.alzahra.offerBaz.enums.ProductType;
import ir.alzahra.offerBaz.exception.BaseException;
import ir.alzahra.offerBaz.proxy.IOfferProxy;
import ir.alzahra.offerBaz.view.beans.BaseBean;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author: hanieh Moafi
 * @Date: 5/5/2019
 **/
@Component("insertProductViewBean")
@Scope("view")
public class InsertProductViewBean extends BaseBean {

    @Autowired
    private IOfferProxy offerProxy;

    private ProductDTO productDTO;
    private List<BankDTO> bankDTOS;
    private BankDTO selectedBank;
    private Long bankId;
    private List<ProductType> productTypes= new ArrayList<>();
    private List<BorrowType> borrowTypes = new ArrayList<>();
    private List<FundType> fundTypes = new ArrayList<>();


    public void init() {
        try {
            bankDTOS = offerProxy.getAllBanks();
            productTypes = Arrays.asList(ProductType.values());
            borrowTypes=Arrays.asList(BorrowType.values());
            fundTypes=Arrays.asList(FundType.values());

        } catch (BaseException e) {
            //TODO
        }
        if (Objects.isNull(productDTO))
            emptyPage();


    }


    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public List<BankDTO> getBankDTOS() {
        return bankDTOS;
    }

    public void setBankDTOS(List<BankDTO> bankDTOS) {
        this.bankDTOS = bankDTOS;
    }

    public BankDTO getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(BankDTO selectedBank) {
        this.selectedBank = selectedBank;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public List<BorrowType> getBorrowTypes() {
        return borrowTypes;
    }

    public void setBorrowTypes(List<BorrowType> borrowTypes) {
        this.borrowTypes = borrowTypes;
    }

    public List<FundType> getFundTypes() {
        return fundTypes;
    }

    public void setFundTypes(List<FundType> fundTypes) {
        this.fundTypes = fundTypes;
    }

    private void emptyPage() {
        productDTO = new ProductDTO();
     //   bankDTOS = new ArrayList<>();


    }

    public void findBank() {
        for (BankDTO b : bankDTOS
        ) {
            if (b.getId() == bankId)
                selectedBank = b;

        }

    }

    public void insert() {
        try {
            if (Objects.nonNull(productDTO) && Objects.nonNull(selectedBank)) {
                BankDTO b= offerProxy.findBankByName(selectedBank.getName());
                productDTO.setDtoState(DtoState.New);
                b.getProducts().add(productDTO);
                offerProxy.updateBank(b);
                addNotificationMessage();
                emptyPage();
                //  init();
            } else {
                throw new BaseException("product.insert.bankNotSelect");
            }
        } catch (BaseException e) {
            handleBaseException(e);
        }


    }

    public void onProductAdded() {
        try {
            bankDTOS = offerProxy.getAllBanks();
        } catch (BaseException e) {
            //TODO
        }
    }
}

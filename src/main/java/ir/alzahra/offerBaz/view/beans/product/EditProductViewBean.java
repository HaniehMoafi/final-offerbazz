package ir.alzahra.offerBaz.view.beans.product;

import ir.alzahra.offerBaz.dto.BankDTO;
import ir.alzahra.offerBaz.dto.ProductDTO;
import ir.alzahra.offerBaz.dto.searchParameter.ProductSearchParam;
import ir.alzahra.offerBaz.enums.BorrowType;
import ir.alzahra.offerBaz.enums.DtoState;
import ir.alzahra.offerBaz.enums.FundType;
import ir.alzahra.offerBaz.enums.ProductType;
import ir.alzahra.offerBaz.exception.BaseException;
import ir.alzahra.offerBaz.proxy.IOfferProxy;
import ir.alzahra.offerBaz.view.beans.BaseBean;
import ir.alzahra.offerBaz.view.util.GeneralUtil;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author z.moafi
 * @since 28/08/2019
 */
@Component("editProductViewBean")
@Scope("view")
public class EditProductViewBean extends BaseBean {

    private String productCode;
    private ProductSearchParam productSearchParam;
    private List<BankDTO> bankDTOS;
    private ProductDTO selectedProduct;
    private String bankName;
    private List<ProductType> productTypes= new ArrayList<>();
    private List<BorrowType> borrowTypes = new ArrayList<>();
    private List<FundType> fundTypes = new ArrayList<>();

    @Autowired
    private IOfferProxy offerProxy;


    public void init() {
        emptyPage();

        try {
            bankDTOS = offerProxy.getAllBanks();
        } catch (BaseException e) {
            //TODO
        }

    }

    private void emptyPage() {
        productSearchParam = new ProductSearchParam();
        selectedProduct = new ProductDTO();
        bankName = "";
        productCode = "";
        productTypes = Arrays.asList(ProductType.values());
        borrowTypes=Arrays.asList(BorrowType.values());
        fundTypes=Arrays.asList(FundType.values());

    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public ProductSearchParam getProductSearchParam() {
        return productSearchParam;
    }

    public void setProductSearchParam(ProductSearchParam productSearchParam) {
        this.productSearchParam = productSearchParam;
    }

    public List<BankDTO> getBankDTOS() {
        return bankDTOS;
    }

    public void setBankDTOS(List<BankDTO> bankDTOS) {
        this.bankDTOS = bankDTOS;
    }

    public ProductDTO getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductDTO selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public void searchProduct() {
        if (Objects.equals(productCode, "")) {
            GeneralUtil.openWindow("includes/searchProduct", new Object[]{true, "850", "520", "100%", "100%", false, false}, "viewObject", null, "viewObject");

        } else {
            try {
                selectedProduct = offerProxy.findProduct(productCode);
                bankName = offerProxy.findBankByAbbreviation(selectedProduct.getUniqueCode().substring(6, 9));
                addNotificationMessage();
                emptyPage();
            } catch (BaseException e) {
                handleBaseException(e);
            }
        }

    }

    public void edit() {
        if (Objects.nonNull(selectedProduct.getId())) {
            try {
                selectedProduct.setDtoState(DtoState.Edit);
                offerProxy.editProduct(selectedProduct);
                addNotificationMessage();
                emptyPage();
            } catch (BaseException e) {
                handleBaseException(e);
            }
        } else {
            handleBaseException(new BaseException("product.edit.notFoundForEdit"));
            emptyPage();
        }

    }

    public void delete() {
        if (Objects.nonNull(selectedProduct.getId())) {
            selectedProduct.setDtoState(DtoState.Delete);
            try {
                offerProxy.deleteProduct(selectedProduct);
                addNotificationMessage();
                emptyPage();
            } catch (BaseException e) {
                handleBaseException(e);
            }
        } else {
            handleBaseException(new BaseException("product.delete.notFoundForDelete"));
        }
    }

    public void onProductSelected(SelectEvent event) {

        Object returnObj = event.getObject();
        if (returnObj != null) {
            selectedProduct = (ProductDTO) returnObj;
            productCode = selectedProduct.getUniqueCode();
            try {
                bankName = offerProxy.findBankByAbbreviation(selectedProduct.getUniqueCode().substring(6, 9));
            } catch (BaseException e) {
                //TODO
            }


        }
    }
}

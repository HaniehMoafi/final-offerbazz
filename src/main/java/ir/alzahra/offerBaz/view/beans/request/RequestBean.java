package ir.alzahra.offerBaz.view.beans.request;

import ir.alzahra.offerBaz.dto.OfferDTO;
import ir.alzahra.offerBaz.dto.OfferRequestDTO;
import ir.alzahra.offerBaz.enums.UnitMoney;
import ir.alzahra.offerBaz.exception.BaseException;
import ir.alzahra.offerBaz.proxy.IOfferProxy;
import ir.alzahra.offerBaz.view.beans.BaseBean;
import ir.alzahra.offerBaz.view.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zahra soltaninejad
 * @Date: 10/21/2019, Mon
 **/


@Component("requestBean")
@Scope("view")
public class RequestBean extends BaseBean {

    @Autowired
    private IOfferProxy offerProxy;

    private OfferRequestDTO offerRequestDTO;
    private List<UnitMoney> unitMonies;
    private OfferDTO selectedOffer;

    public void init(){
        unitMonies= Arrays.asList(UnitMoney.values());
        if (Objects.isNull(offerRequestDTO))
            emptyPage();
    }

    public OfferRequestDTO getOfferRequestDTO() {
        return offerRequestDTO;
    }

    public void setOfferRequestDTO(OfferRequestDTO offerRequestDTO) {
        this.offerRequestDTO = offerRequestDTO;
    }

    private void emptyPage() {
        offerRequestDTO=new OfferRequestDTO();
    }

    public List<UnitMoney> getUnitMonies() {
        return unitMonies;
    }

    public void setUnitMonies(List<UnitMoney> unitMonies) {
        this.unitMonies = unitMonies;
    }

    public OfferDTO getSelectedOffer() {
        return selectedOffer;
    }

    public void setSelectedOffer(OfferDTO selectedOffer) {
        this.selectedOffer = selectedOffer;
    }

    public void insert(){
        try {
            offerRequestDTO=offerProxy.insertRequest(offerRequestDTO);
            addNotificationMessage();
          //  emptyPage();
        } catch (BaseException e) {
            handleBaseException(e);
        }


    }
    public void redirectViewProduct(){
        if (Objects.nonNull(selectedOffer.getProduct()))
            GeneralUtil.openWindow("includes/viewPro", new Object[]{true, "850", "850", "100%", "100%", false, false}, "viewObject", selectedOffer.getProduct(), "viewObject");
    }


}

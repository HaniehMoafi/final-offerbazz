package ir.alzahra.offerBaz.view.beans.request;

import ir.alzahra.offerBaz.dto.OfferRequestDTO;
import ir.alzahra.offerBaz.exception.BaseException;
import ir.alzahra.offerBaz.proxy.IOfferProxy;
import ir.alzahra.offerBaz.view.beans.BaseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    public void init(){
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

    public void insert(){
        try {
            offerProxy.insertRequest(offerRequestDTO);
            addNotificationMessage();
            emptyPage();
        } catch (BaseException e) {
            handleBaseException(e);
        }


    }
}

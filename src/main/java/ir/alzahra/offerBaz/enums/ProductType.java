package ir.alzahra.offerBaz.enums;

/**
 * @author z.moafi
 * @since 13/10/2019
 */
public enum ProductType {

    BORROW("قرض الحسنه"),
    FUND("سرمایه گذاری");


    private final String value;

    private ProductType(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

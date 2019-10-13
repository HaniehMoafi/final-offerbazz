package ir.alzahra.offerBaz.enums;

/**
 * @author z.moafi
 * @since 13/10/2019
 */
public enum BorrowType {


    CURRENT_ACCOUNT("حساب جاری"),
    SAVE_ACCOUNT("پس انداز");


    private final String value;

    private BorrowType(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}

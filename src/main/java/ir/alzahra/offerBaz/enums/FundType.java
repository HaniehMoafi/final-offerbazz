package ir.alzahra.offerBaz.enums;

/**
 * @author z.moafi
 * @since 13/10/2019
 */
public enum FundType {

    LONG_TERM("بلند مدت"),
    SHORT_TERM("کوتاه مدت");


    private final String value;

    private FundType(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

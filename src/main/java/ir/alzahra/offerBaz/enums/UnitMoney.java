package ir.alzahra.offerBaz.enums;

/**
 * @author z.moafi
 * @since 31/10/2019
 */
public enum UnitMoney {

    TOMAN("تومان"),
    DOLAR("دلار"),
   RIAL("ریال");



    private final String value;

    private UnitMoney(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}

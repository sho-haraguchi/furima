package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryFee {
    COD(1, "着払い(購入者負担)"),
    INCLUDED(2, "送料込み(出品者負担)");

    private final int code;
    private final String displayName;

    public static DeliveryFee fromCode(int code) {
        for (DeliveryFee fee : DeliveryFee.values()) {
            if (fee.getCode() == code) {
                return fee;
            }
        }
        throw new IllegalArgumentException("不正な配送料負担コードです: " + code);
    }
}
package computer.shop.models.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CouponBuyBindingModel {
    private String couponName;

    public CouponBuyBindingModel() {
    }

    @NotNull
    @NotBlank
    public String getCouponName() {
        return couponName;
    }

    public CouponBuyBindingModel setCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }
}

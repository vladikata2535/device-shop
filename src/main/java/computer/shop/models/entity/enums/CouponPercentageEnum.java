package computer.shop.models.entity.enums;

public enum CouponPercentageEnum {
    ten(10),
    fifteen(15),
    twenty(20),
    twentyFive(25),
    fifty(50);

    public final Integer label;

    CouponPercentageEnum(Integer label) {
        this.label = label;
    }
}

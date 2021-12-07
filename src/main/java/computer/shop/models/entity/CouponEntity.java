package computer.shop.models.entity;

import computer.shop.models.entity.enums.CouponPercentageEnum;

import javax.persistence.*;

@Entity
@Table(name = "coupons")
public class CouponEntity extends BaseEntity{
    private CouponPercentageEnum percentage;
    private String description;
    private boolean isActivated;

    public CouponEntity() {
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public CouponPercentageEnum getPercentage() {
        return percentage;
    }

    public CouponEntity setPercentage(CouponPercentageEnum percentage) {
        this.percentage = percentage;
        return this;
    }

    @Column(nullable = false)
    @Lob
    public String getDescription() {
        return description;
    }

    public CouponEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public boolean isActivated() {
        return isActivated;
    }

    public CouponEntity setActivated(boolean activated) {
        isActivated = activated;
        return this;
    }
}

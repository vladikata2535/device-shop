package computer.shop.models.view;

public class CouponViewModel {
    private Long id;
    private Integer percentageNumber;
    private String description;
    private String name;

    public CouponViewModel() {
    }

    public Integer getPercentageNumber() {
        return percentageNumber;
    }

    public CouponViewModel setPercentageNumber(Integer percentageNumber) {
        this.percentageNumber = percentageNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CouponViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CouponViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CouponViewModel setName(String name) {
        this.name = name;
        return this;
    }
}

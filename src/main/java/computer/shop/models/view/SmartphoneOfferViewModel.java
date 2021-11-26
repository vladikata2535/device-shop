package computer.shop.models.view;

import computer.shop.models.entity.SmartphoneEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SmartphoneOfferViewModel {
    private String name;
    private LocalDate createdOn;
    private BigDecimal price;
    private Long reviews;
    private Long id;
    private String modelName;

    public SmartphoneOfferViewModel() {
    }

    public Long getReviews() {
        return reviews;
    }

    public SmartphoneOfferViewModel setReviews(Long reviews) {
        this.reviews = reviews;
        return this;
    }

    public String getName() {
        return name;
    }

    public SmartphoneOfferViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public SmartphoneOfferViewModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneOfferViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getId() {
        return id;
    }

    public SmartphoneOfferViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public SmartphoneOfferViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }
}

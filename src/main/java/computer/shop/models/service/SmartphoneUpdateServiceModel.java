package computer.shop.models.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SmartphoneUpdateServiceModel {
    private Long id;
    private String name;
    private LocalDate createdOn;
    private String description;
    private String smartphoneModelName;
    private BigDecimal price;

    public SmartphoneUpdateServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public SmartphoneUpdateServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SmartphoneUpdateServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public SmartphoneUpdateServiceModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SmartphoneUpdateServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSmartphoneModelName() {
        return smartphoneModelName;
    }

    public SmartphoneUpdateServiceModel setSmartphoneModelName(String smartphoneModelName) {
        this.smartphoneModelName = smartphoneModelName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneUpdateServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}

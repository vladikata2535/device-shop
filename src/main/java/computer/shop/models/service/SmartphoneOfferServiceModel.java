package computer.shop.models.service;

import java.time.LocalDate;

public class SmartphoneOfferServiceModel {
    private Long id;
    private String name;
    private LocalDate createdOn;
    private String description;
    private String smartphone;

    public SmartphoneOfferServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public SmartphoneOfferServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SmartphoneOfferServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public SmartphoneOfferServiceModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SmartphoneOfferServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSmartphone() {
        return smartphone;
    }

    public SmartphoneOfferServiceModel setSmartphone(String smartphone) {
        this.smartphone = smartphone;
        return this;
    }
}

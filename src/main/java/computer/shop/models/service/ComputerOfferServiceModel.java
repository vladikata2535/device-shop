package computer.shop.models.service;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComputerOfferServiceModel {
    private Long id;
    private String name;
    private LocalDate createdOn;
    private String description;
    private String computer;

    public ComputerOfferServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ComputerOfferServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComputerOfferServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ComputerOfferServiceModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ComputerOfferServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getComputer() {
        return computer;
    }

    public ComputerOfferServiceModel setComputer(String computer) {
        this.computer = computer;
        return this;
    }
}

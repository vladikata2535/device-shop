package computer.shop.models.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComputerUpdateServiceModel {
    private String name;
    private LocalDate createdOn;
    private String computerName;
    private String description;
    private Long id;
    private BigDecimal price;

    public ComputerUpdateServiceModel() {
    }

    public String getName() {
        return name;
    }

    public ComputerUpdateServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ComputerUpdateServiceModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getComputerName() {
        return computerName;
    }

    public ComputerUpdateServiceModel setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ComputerUpdateServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ComputerUpdateServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ComputerUpdateServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}

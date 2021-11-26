package computer.shop.models.view;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComputerOfferViewModel {
    private String name;
    private LocalDate createdOn;
    private BigDecimal price;
    private Long reviews;
    private Long id;
    private String computerName;

    public ComputerOfferViewModel() {
    }

    public String getName() {
        return name;
    }

    public ComputerOfferViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ComputerOfferViewModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ComputerOfferViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getReviews() {
        return reviews;
    }

    public ComputerOfferViewModel setReviews(Long reviews) {
        this.reviews = reviews;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ComputerOfferViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getComputerName() {
        return computerName;
    }

    public ComputerOfferViewModel setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }
}

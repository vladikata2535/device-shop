package computer.shop.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "computer_offers")
public class ComputerOfferEntity extends BaseEntity{
    private String name;
    private LocalDate createdOn;
    private String description;
    private BigDecimal price;
    private UserEntity seller;
    private ComputerEntity computer;

    public ComputerOfferEntity() {
    }

    @Column(nullable = false)
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ComputerOfferEntity setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public ComputerOfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public ComputerOfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @ManyToOne
    public UserEntity getSeller() {
        return seller;
    }

    public ComputerOfferEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    @ManyToOne
    public ComputerEntity getComputer() {
        return computer;
    }

    public ComputerOfferEntity setComputer(ComputerEntity computer) {
        this.computer = computer;
        return this;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public ComputerOfferEntity setName(String name) {
        this.name = name;
        return this;
    }
}

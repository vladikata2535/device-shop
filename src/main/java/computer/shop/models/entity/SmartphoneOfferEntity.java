package computer.shop.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "smartphone_offers")
public class SmartphoneOfferEntity extends BaseEntity{
    private String name;
    private LocalDate createdOn;
    private String description;
    private BigDecimal price;
    private UserEntity seller;
    private SmartphoneEntity smartphone;

    public SmartphoneOfferEntity() {
    }

    @Column(nullable = false)
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public SmartphoneOfferEntity setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public SmartphoneOfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneOfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @ManyToOne
    public UserEntity getSeller() {
        return seller;
    }

    public SmartphoneOfferEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    @ManyToOne
    public SmartphoneEntity getSmartphone() {
        return smartphone;
    }

    public SmartphoneOfferEntity setSmartphone(SmartphoneEntity smartphone) {
        this.smartphone = smartphone;
        return this;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public SmartphoneOfferEntity setName(String name) {
        this.name = name;
        return this;
    }
}

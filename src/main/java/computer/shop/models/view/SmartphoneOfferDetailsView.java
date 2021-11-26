package computer.shop.models.view;

import computer.shop.models.dto.SmartphoneDTO;
import computer.shop.models.entity.SmartphoneEntity;

import java.math.BigDecimal;

public class SmartphoneOfferDetailsView {
    private Long id;
    private String name;
    private BigDecimal price;
    private String sellerFullName;
    private SmartphoneDTO smartphone;

    public Long getId() {
        return id;
    }

    public SmartphoneOfferDetailsView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SmartphoneOfferDetailsView setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneOfferDetailsView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSellerFullName() {
        return sellerFullName;
    }

    public SmartphoneOfferDetailsView setSellerFullName(String sellerFullName) {
        this.sellerFullName = sellerFullName;
        return this;
    }

    public SmartphoneDTO getSmartphone() {
        return smartphone;
    }

    public SmartphoneOfferDetailsView setSmartphone(SmartphoneDTO smartphone) {
        this.smartphone = smartphone;
        return this;
    }
}

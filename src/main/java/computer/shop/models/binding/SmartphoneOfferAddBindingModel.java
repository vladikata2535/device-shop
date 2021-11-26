package computer.shop.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class SmartphoneOfferAddBindingModel {

    private String name;
    private LocalDate createdOn;
    private String description;
    private String smartphone;

    public SmartphoneOfferAddBindingModel() {
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public SmartphoneOfferAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public SmartphoneOfferAddBindingModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @NotBlank
    @Size(min = 5)
    public String getDescription() {
        return description;
    }

    public SmartphoneOfferAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @NotNull
    @NotBlank
    public String getSmartphone() {
        return smartphone;
    }

    public SmartphoneOfferAddBindingModel setSmartphone(String smartphone) {
        this.smartphone = smartphone;
        return this;
    }
}

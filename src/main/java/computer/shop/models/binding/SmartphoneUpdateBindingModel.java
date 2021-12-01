package computer.shop.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class SmartphoneUpdateBindingModel {

    private Long id;
    private String name;
    private LocalDate createdOn;
    private String description;
    private String smartphoneModelName;

    public SmartphoneUpdateBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public SmartphoneUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public SmartphoneUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public SmartphoneUpdateBindingModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @NotBlank
    @Size(min = 5)
    public String getDescription() {
        return description;
    }

    public SmartphoneUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @NotNull
    @NotBlank
    public String getSmartphoneModelName() {
        return smartphoneModelName;
    }

    public SmartphoneUpdateBindingModel setSmartphoneModelName(String smartphoneModelName) {
        this.smartphoneModelName = smartphoneModelName;
        return this;
    }
}

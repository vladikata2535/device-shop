package computer.shop.models.binding;

import computer.shop.models.entity.ComputerEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ComputerOfferAddBindingModel {
    private String name;
    private LocalDate createdOn;
    private String computer;
    private String description;

    public ComputerOfferAddBindingModel() {
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public ComputerOfferAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ComputerOfferAddBindingModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @NotNull
    @NotBlank
    public String getComputer() {
        return computer;
    }

    public ComputerOfferAddBindingModel setComputer(String computer) {
        this.computer = computer;
        return this;
    }

    @Size(min = 5)
    @NotBlank
    public String getDescription() {
        return description;
    }

    public ComputerOfferAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}

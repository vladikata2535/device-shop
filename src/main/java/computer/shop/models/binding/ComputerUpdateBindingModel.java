package computer.shop.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ComputerUpdateBindingModel {
    private String name;
    private LocalDate createdOn;
    private String computerName;
    private String description;
    private Long id;

    public ComputerUpdateBindingModel() {
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public ComputerUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ComputerUpdateBindingModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @NotNull
    @NotBlank
    public String getComputerName() {
        return computerName;
    }

    public ComputerUpdateBindingModel setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    @Size(min = 5)
    @NotBlank
    public String getDescription() {
        return description;
    }

    public ComputerUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ComputerUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
}

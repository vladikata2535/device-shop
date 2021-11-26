package computer.shop.models.binding;

import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;
import computer.shop.models.entity.enums.componentsEnums.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ComputerAddBindingModel {
    private String name;
    private String description;
    private LocalDate manufacturedOn;
    private ComputerBoxEnum computerBox;


    private VideoCardEnum videoCard;
    private ProcessorEnum processor;
    private RandomAccessMemoryEnum ram;
    private PowerSupplyEnum powerSupply;
    private DriveEnum drive;

    public ComputerAddBindingModel() {
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public ComputerAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @Size(min = 5)
    @NotBlank
    public String getDescription() {
        return description;
    }

    public ComputerAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public ComputerAddBindingModel setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    @NotNull
    public ComputerBoxEnum getComputerBox() {
        return computerBox;
    }

    public ComputerAddBindingModel setComputerBox(ComputerBoxEnum computerBox) {
        this.computerBox = computerBox;
        return this;
    }

    @NotNull
    public VideoCardEnum getVideoCard() {
        return videoCard;
    }

    public ComputerAddBindingModel setVideoCard(VideoCardEnum videoCard) {
        this.videoCard = videoCard;
        return this;
    }

    @NotNull
    public ProcessorEnum getProcessor() {
        return processor;
    }

    public ComputerAddBindingModel setProcessor(ProcessorEnum processor) {
        this.processor = processor;
        return this;
    }

    @NotNull
    public RandomAccessMemoryEnum getRam() {
        return ram;
    }

    public ComputerAddBindingModel setRam(RandomAccessMemoryEnum ram) {
        this.ram = ram;
        return this;
    }

    @NotNull
    public PowerSupplyEnum getPowerSupply() {
        return powerSupply;
    }

    public ComputerAddBindingModel setPowerSupply(PowerSupplyEnum powerSupply) {
        this.powerSupply = powerSupply;
        return this;
    }

    @NotNull
    public DriveEnum getDrive() {
        return drive;
    }

    public ComputerAddBindingModel setDrive(DriveEnum drive) {
        this.drive = drive;
        return this;
    }
}

package computer.shop.models.service;

import computer.shop.models.entity.enums.ComputerBoxEnum;
import computer.shop.models.entity.enums.componentsEnums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComputerServiceModel {
    private Long id;
    private String name;
    private String description;
    private LocalDate manufacturedOn;
    private ComputerBoxEnum computerBox;
    private BigDecimal price;

    private VideoCardEnum videoCard;
    private ProcessorEnum processor;
    private RandomAccessMemoryEnum ram;
    private PowerSupplyEnum powerSupply;
    private DriveEnum drive;

    public ComputerServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ComputerServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComputerServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ComputerServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public ComputerServiceModel setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    public ComputerBoxEnum getComputerBox() {
        return computerBox;
    }

    public ComputerServiceModel setComputerBox(ComputerBoxEnum computerBox) {
        this.computerBox = computerBox;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ComputerServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public VideoCardEnum getVideoCard() {
        return videoCard;
    }

    public ComputerServiceModel setVideoCard(VideoCardEnum videoCard) {
        this.videoCard = videoCard;
        return this;
    }

    public ProcessorEnum getProcessor() {
        return processor;
    }

    public ComputerServiceModel setProcessor(ProcessorEnum processor) {
        this.processor = processor;
        return this;
    }

    public RandomAccessMemoryEnum getRam() {
        return ram;
    }

    public ComputerServiceModel setRam(RandomAccessMemoryEnum ram) {
        this.ram = ram;
        return this;
    }

    public PowerSupplyEnum getPowerSupply() {
        return powerSupply;
    }

    public ComputerServiceModel setPowerSupply(PowerSupplyEnum powerSupply) {
        this.powerSupply = powerSupply;
        return this;
    }

    public DriveEnum getDrive() {
        return drive;
    }

    public ComputerServiceModel setDrive(DriveEnum drive) {
        this.drive = drive;
        return this;
    }
}

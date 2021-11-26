package computer.shop.models.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SmartphoneServiceModel {
    private Long id;
    private String companyName;
    private String modelName;
    private String operationalSystem;
    private String chipset;
    private String processor;
    private Integer ram;
    private Integer builtInMemory;
    private Integer frontCameraMP;
    private Integer backCameraMP;
    private Integer batteryCapacity;
    private Double displayInches;
    private BigDecimal price;
    private LocalDate manufacturedOn;
    private String description;

    public SmartphoneServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public SmartphoneServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public SmartphoneServiceModel setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public SmartphoneServiceModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getOperationalSystem() {
        return operationalSystem;
    }

    public SmartphoneServiceModel setOperationalSystem(String operationalSystem) {
        this.operationalSystem = operationalSystem;
        return this;
    }

    public String getChipset() {
        return chipset;
    }

    public SmartphoneServiceModel setChipset(String chipset) {
        this.chipset = chipset;
        return this;
    }

    public String getProcessor() {
        return processor;
    }

    public SmartphoneServiceModel setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public Integer getRam() {
        return ram;
    }

    public SmartphoneServiceModel setRam(Integer ram) {
        this.ram = ram;
        return this;
    }

    public Integer getBuiltInMemory() {
        return builtInMemory;
    }

    public SmartphoneServiceModel setBuiltInMemory(Integer builtInMemory) {
        this.builtInMemory = builtInMemory;
        return this;
    }

    public Integer getFrontCameraMP() {
        return frontCameraMP;
    }

    public SmartphoneServiceModel setFrontCameraMP(Integer frontCameraMP) {
        this.frontCameraMP = frontCameraMP;
        return this;
    }

    public Integer getBackCameraMP() {
        return backCameraMP;
    }

    public SmartphoneServiceModel setBackCameraMP(Integer backCameraMP) {
        this.backCameraMP = backCameraMP;
        return this;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public SmartphoneServiceModel setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public Double getDisplayInches() {
        return displayInches;
    }

    public SmartphoneServiceModel setDisplayInches(Double displayInches) {
        this.displayInches = displayInches;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public SmartphoneServiceModel setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SmartphoneServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }
}

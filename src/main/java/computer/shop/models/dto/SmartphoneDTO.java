package computer.shop.models.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SmartphoneDTO {
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
    private LocalDate manufacturedOn;
    private String description;

    public SmartphoneDTO() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public SmartphoneDTO setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public SmartphoneDTO setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getOperationalSystem() {
        return operationalSystem;
    }

    public SmartphoneDTO setOperationalSystem(String operationalSystem) {
        this.operationalSystem = operationalSystem;
        return this;
    }

    public String getChipset() {
        return chipset;
    }

    public SmartphoneDTO setChipset(String chipset) {
        this.chipset = chipset;
        return this;
    }

    public String getProcessor() {
        return processor;
    }

    public SmartphoneDTO setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public Integer getRam() {
        return ram;
    }

    public SmartphoneDTO setRam(Integer ram) {
        this.ram = ram;
        return this;
    }

    public Integer getBuiltInMemory() {
        return builtInMemory;
    }

    public SmartphoneDTO setBuiltInMemory(Integer builtInMemory) {
        this.builtInMemory = builtInMemory;
        return this;
    }

    public Integer getFrontCameraMP() {
        return frontCameraMP;
    }

    public SmartphoneDTO setFrontCameraMP(Integer frontCameraMP) {
        this.frontCameraMP = frontCameraMP;
        return this;
    }

    public Integer getBackCameraMP() {
        return backCameraMP;
    }

    public SmartphoneDTO setBackCameraMP(Integer backCameraMP) {
        this.backCameraMP = backCameraMP;
        return this;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public SmartphoneDTO setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public Double getDisplayInches() {
        return displayInches;
    }

    public SmartphoneDTO setDisplayInches(Double displayInches) {
        this.displayInches = displayInches;
        return this;
    }

    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public SmartphoneDTO setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SmartphoneDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}

package computer.shop.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SmartphoneAddBindingModel {
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

    public SmartphoneAddBindingModel() {
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getCompanyName() {
        return companyName;
    }

    public SmartphoneAddBindingModel setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getModelName() {
        return modelName;
    }

    public SmartphoneAddBindingModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    @Size(max = 20)
    @NotBlank
    public String getOperationalSystem() {
        return operationalSystem;
    }

    public SmartphoneAddBindingModel setOperationalSystem(String operationalSystem) {
        this.operationalSystem = operationalSystem;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getChipset() {
        return chipset;
    }

    public SmartphoneAddBindingModel setChipset(String chipset) {
        this.chipset = chipset;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getProcessor() {
        return processor;
    }

    public SmartphoneAddBindingModel setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    @Positive
    @NotNull
    public Integer getRam() {
        return ram;
    }

    public SmartphoneAddBindingModel setRam(Integer ram) {
        this.ram = ram;
        return this;
    }

    @Positive
    @NotNull
    public Integer getBuiltInMemory() {
        return builtInMemory;
    }

    public SmartphoneAddBindingModel setBuiltInMemory(Integer builtInMemory) {
        this.builtInMemory = builtInMemory;
        return this;
    }

    @Positive
    @NotNull
    public Integer getFrontCameraMP() {
        return frontCameraMP;
    }

    public SmartphoneAddBindingModel setFrontCameraMP(Integer frontCameraMP) {
        this.frontCameraMP = frontCameraMP;
        return this;
    }

    @Positive
    @NotNull
    public Integer getBackCameraMP() {
        return backCameraMP;
    }

    public SmartphoneAddBindingModel setBackCameraMP(Integer backCameraMP) {
        this.backCameraMP = backCameraMP;
        return this;
    }

    @Positive
    @NotNull
    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public SmartphoneAddBindingModel setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    @Positive
    @NotNull
    public Double getDisplayInches() {
        return displayInches;
    }

    public SmartphoneAddBindingModel setDisplayInches(Double displayInches) {
        this.displayInches = displayInches;
        return this;
    }

    @Positive
    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public SmartphoneAddBindingModel setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    @Size(min = 5)
    @NotBlank
    public String getDescription() {
        return description;
    }

    public SmartphoneAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}

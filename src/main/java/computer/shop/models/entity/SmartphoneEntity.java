package computer.shop.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "smartphones")
public class SmartphoneEntity extends BaseEntity{
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
    private boolean isPublished;

    public SmartphoneEntity() {
    }

    @Column(nullable = false)
    public String getModelName() {
        return modelName;
    }

    public SmartphoneEntity setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    @Column(nullable = false)
    public String getOperationalSystem() {
        return operationalSystem;
    }

    public SmartphoneEntity setOperationalSystem(String operationalSystem) {
        this.operationalSystem = operationalSystem;
        return this;
    }

    @Column(nullable = false)
    public String getChipset() {
        return chipset;
    }

    public SmartphoneEntity setChipset(String chipset) {
        this.chipset = chipset;
        return this;
    }

    @Column(nullable = false)
    public String getProcessor() {
        return processor;
    }

    public SmartphoneEntity setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    @Column(nullable = false)
    public Integer getRam() {
        return ram;
    }

    public SmartphoneEntity setRam(Integer ram) {
        this.ram = ram;
        return this;
    }

    @Column(nullable = false)
    public Integer getBuiltInMemory() {
        return builtInMemory;
    }

    public SmartphoneEntity setBuiltInMemory(Integer builtInMemory) {
        this.builtInMemory = builtInMemory;
        return this;
    }

    @Column(nullable = false)
    public Integer getFrontCameraMP() {
        return frontCameraMP;
    }

    public SmartphoneEntity setFrontCameraMP(Integer frontCameraMP) {
        this.frontCameraMP = frontCameraMP;
        return this;
    }

    @Column(nullable = false)
    public Integer getBackCameraMP() {
        return backCameraMP;
    }

    public SmartphoneEntity setBackCameraMP(Integer backCameraMP) {
        this.backCameraMP = backCameraMP;
        return this;
    }

    @Column(nullable = false)
    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public SmartphoneEntity setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public SmartphoneEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public Double getDisplayInches() {
        return displayInches;
    }

    public SmartphoneEntity setDisplayInches(Double displayInches) {
        this.displayInches = displayInches;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public SmartphoneEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public SmartphoneEntity setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    @Column(nullable = false)
    public String getCompanyName() {
        return companyName;
    }

    public SmartphoneEntity setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    @Column(nullable = false)
    public boolean isPublished() {
        return isPublished;
    }

    public SmartphoneEntity setPublished(boolean published) {
        isPublished = published;
        return this;
    }
}

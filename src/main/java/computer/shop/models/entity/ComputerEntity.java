package computer.shop.models.entity;

import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "computers")
public class ComputerEntity extends BaseEntity{
    private String name;
    private String description;
    private LocalDate manufacturedOn;
    private ComputerBoxEnum computerBox;
    private BigDecimal price;
    private boolean isPublished;

    private VideoCardEntity videoCard;
    private ProcessorEntity processor;
    private RandomAccessMemoryEntity ram;
    private PowerSupplyEntity powerSupply;
    private DriveEntity drive;

    public ComputerEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public ComputerEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    @Lob
    public String getDescription() {
        return description;
    }

    public ComputerEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getManufacturedOn() {
        return manufacturedOn;
    }

    public ComputerEntity setManufacturedOn(LocalDate manufacturedOn) {
        this.manufacturedOn = manufacturedOn;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ComputerBoxEnum getComputerBox() {
        return computerBox;
    }

    public ComputerEntity setComputerBox(ComputerBoxEnum computerBox) {
        this.computerBox = computerBox;
        return this;
    }

    @ManyToOne
    public VideoCardEntity getVideoCard() {
        return videoCard;
    }

    public ComputerEntity setVideoCard(VideoCardEntity videoCard) {
        this.videoCard = videoCard;
        return this;
    }

    @ManyToOne
    public ProcessorEntity getProcessor() {
        return processor;
    }

    public ComputerEntity setProcessor(ProcessorEntity processor) {
        this.processor = processor;
        return this;
    }

    @ManyToOne
    public RandomAccessMemoryEntity getRam() {
        return ram;
    }

    public ComputerEntity setRam(RandomAccessMemoryEntity ram) {
        this.ram = ram;
        return this;
    }

    @ManyToOne
    public PowerSupplyEntity getPowerSupply() {
        return powerSupply;
    }

    public ComputerEntity setPowerSupply(PowerSupplyEntity powerSupply) {
        this.powerSupply = powerSupply;
        return this;
    }

    @ManyToOne
    public DriveEntity getDrive() {
        return drive;
    }

    public ComputerEntity setDrive(DriveEntity drive) {
        this.drive = drive;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public ComputerEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column(nullable = false)
    public boolean isPublished() {
        return isPublished;
    }

    public ComputerEntity setPublished(boolean published) {
        isPublished = published;
        return this;
    }
}

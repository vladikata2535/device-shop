package computer.shop.models.entity.enumEntity;

import computer.shop.models.entity.BaseEntity;
import computer.shop.models.entity.enums.componentsEnums.DriveEnum;
import computer.shop.models.entity.enums.componentsEnums.DriveTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "drives")
public class DriveEntity extends BaseEntity {
    private DriveEnum name;
    private String capacity;
    private Integer warranty;
    private String description;
    private DriveTypeEnum driveType;
    private BigDecimal price;

    public DriveEntity(DriveEnum name, String capacity, Integer warranty, String description, DriveTypeEnum driveType, BigDecimal price) {
        this.setName(name);
        this.setCapacity(capacity);
        this.setWarranty(warranty);
        this.setDescription(description);
        this.setDriveType(driveType);
        this.setPrice(price);
    }

    public DriveEntity() {

    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true)
    public DriveEnum getName() {
        return name;
    }

    public DriveEntity setName(DriveEnum name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public String getCapacity() {
        return capacity;
    }

    public DriveEntity setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    @Column(nullable = false)
    public Integer getWarranty() {
        return warranty;
    }

    public DriveEntity setWarranty(Integer warranty) {
        this.warranty = warranty;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public DriveEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public DriveEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public DriveTypeEnum getDriveType() {
        return driveType;
    }

    public DriveEntity setDriveType(DriveTypeEnum driveType) {
        this.driveType = driveType;
        return this;
    }
}

package computer.shop.models.entity.enumEntity;

import computer.shop.models.entity.BaseEntity;
import computer.shop.models.entity.enums.componentsEnums.RandomAccessMemoryEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ram_memories")
public class RandomAccessMemoryEntity extends BaseEntity {
    private RandomAccessMemoryEnum name;
    private String speed;
    private double voltage;
    private String DIMMs;
    private String description;
    private BigDecimal price;

    public RandomAccessMemoryEntity(RandomAccessMemoryEnum name, String speed, double voltage, String DIMMs, String description, BigDecimal price) {
        this.setName(name);
        this.setSpeed(speed);
        this.setVoltage(voltage);
        this.setDIMMs(DIMMs);
        this.setDescription(description);
        this.setPrice(price);
    }

    public RandomAccessMemoryEntity() {
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true)
    public RandomAccessMemoryEnum getName() {
        return name;
    }

    public RandomAccessMemoryEntity setName(RandomAccessMemoryEnum name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public String getSpeed() {
        return speed;
    }

    public RandomAccessMemoryEntity setSpeed(String speed) {
        this.speed = speed;
        return this;
    }

    @Column(nullable = false)
    public double getVoltage() {
        return voltage;
    }

    public RandomAccessMemoryEntity setVoltage(double voltage) {
        this.voltage = voltage;
        return this;
    }

    @Column(nullable = false)
    public String getDIMMs() {
        return DIMMs;
    }

    public RandomAccessMemoryEntity setDIMMs(String DIMMs) {
        this.DIMMs = DIMMs;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public RandomAccessMemoryEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public RandomAccessMemoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}

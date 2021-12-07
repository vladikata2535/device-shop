package computer.shop.models.entity.enumEntity;

import computer.shop.models.entity.BaseEntity;
import computer.shop.models.entity.enums.componentsEnums.PowerSupplyEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "power_supplies")
public class PowerSupplyEntity extends BaseEntity {
    private PowerSupplyEnum name;
    private Integer maxDcOutput;
    private String noise;
    private String cooling;
    private String efficiency;
    private Integer PCIeConnectors;
    private Integer warranty;
    private String description;
    private BigDecimal price;

    public PowerSupplyEntity(PowerSupplyEnum name, Integer maxDcOutput, String noise, String cooling, String efficiency, Integer PCIeConnectors, Integer warranty, String description, BigDecimal price) {
        this.setName(name);
        this.setMaxDcOutput(maxDcOutput);
        this.setNoise(noise);
        this.setCooling(cooling);
        this.setEfficiency(efficiency);
        this.setPCIeConnectors(PCIeConnectors);
        this.setWarranty(warranty);
        this.setDescription(description);
        this.setPrice(price);
    }

    public PowerSupplyEntity() {

    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true)
    public PowerSupplyEnum getName() {
        return name;
    }

    public PowerSupplyEntity setName(PowerSupplyEnum name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public Integer getMaxDcOutput() {
        return maxDcOutput;
    }

    public PowerSupplyEntity setMaxDcOutput(Integer maxDcOutput) {
        this.maxDcOutput = maxDcOutput;
        return this;
    }

    @Column(nullable = false)
    public String getNoise() {
        return noise;
    }

    public PowerSupplyEntity setNoise(String noise) {
        this.noise = noise;
        return this;
    }

    @Column(nullable = false)
    public String getCooling() {
        return cooling;
    }

    public PowerSupplyEntity setCooling(String cooling) {
        this.cooling = cooling;
        return this;
    }

    @Column(nullable = false)
    public String getEfficiency() {
        return efficiency;
    }

    public PowerSupplyEntity setEfficiency(String efficiency) {
        this.efficiency = efficiency;
        return this;
    }

    @Column(nullable = false)
    public Integer getPCIeConnectors() {
        return PCIeConnectors;
    }

    public PowerSupplyEntity setPCIeConnectors(Integer PCIeConnectors) {
        this.PCIeConnectors = PCIeConnectors;
        return this;
    }

    @Column(nullable = false)
    public Integer getWarranty() {
        return warranty;
    }

    public PowerSupplyEntity setWarranty(Integer warranty) {
        this.warranty = warranty;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public PowerSupplyEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public PowerSupplyEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}

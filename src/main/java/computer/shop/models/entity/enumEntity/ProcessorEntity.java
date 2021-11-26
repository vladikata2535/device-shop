package computer.shop.models.entity.enumEntity;

import computer.shop.models.entity.BaseEntity;
import computer.shop.models.entity.enums.componentsEnums.ProcessorEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "processors")
public class ProcessorEntity extends BaseEntity {
    private ProcessorEnum name;
    private String socket;
    private Integer TDP;
    private String description;
    private BigDecimal price;

    public ProcessorEntity(ProcessorEnum name, String socket, Integer TDP, String description, BigDecimal price) {
        this.setName(name);
        this.setSocket(socket);
        this.setTDP(TDP);
        this.setDescription(description);
        this.setPrice(price);
    }

    public ProcessorEntity() {
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public ProcessorEnum getName() {
        return name;
    }

    public ProcessorEntity setName(ProcessorEnum name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public String getSocket() {
        return socket;
    }

    public ProcessorEntity setSocket(String socket) {
        this.socket = socket;
        return this;
    }

    @Column(nullable = false)
    public Integer getTDP() {
        return TDP;
    }

    public ProcessorEntity setTDP(Integer TDP) {
        this.TDP = TDP;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public ProcessorEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public ProcessorEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}

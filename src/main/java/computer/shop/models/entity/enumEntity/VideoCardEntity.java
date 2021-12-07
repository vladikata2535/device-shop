package computer.shop.models.entity.enumEntity;

import computer.shop.models.entity.BaseEntity;
import computer.shop.models.entity.enums.componentsEnums.VideoCardEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "videocards")
public class VideoCardEntity extends BaseEntity {
    private VideoCardEnum name;
    private String memory;
    private Integer gpuCores;
    private String description;
    private BigDecimal price;

    public VideoCardEntity(VideoCardEnum name, String memory, Integer gpuCores, String description, BigDecimal price) {
        this.setName(name);
        this.setMemory(memory);
        this.setGpuCores(gpuCores);
        this.setDescription(description);
        this.setPrice(price);
    }

    public VideoCardEntity() {
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public VideoCardEnum getName() {
        return name;
    }

    public VideoCardEntity setName(VideoCardEnum videoCard) {
        this.name = videoCard;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public VideoCardEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Column(nullable = false)
    public String getMemory() {
        return memory;
    }

    public VideoCardEntity setMemory(String memory) {
        this.memory = memory;
        return this;
    }

    @Column(nullable = false)
    public Integer getGpuCores() {
        return gpuCores;
    }

    public VideoCardEntity setGpuCores(Integer gpuCores) {
        this.gpuCores = gpuCores;
        return this;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public VideoCardEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}

package computer.shop.models.view;

public class SmartphoneWarehouseViewModel {
    private Long id;
    private String modelName;
    private String description;
    private boolean published;

    public SmartphoneWarehouseViewModel() {
    }

    public Long getId() {
        return id;
    }

    public SmartphoneWarehouseViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public SmartphoneWarehouseViewModel setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SmartphoneWarehouseViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isPublished() {
        return published;
    }

    public SmartphoneWarehouseViewModel setPublished(boolean published) {
        this.published = published;
        return this;
    }
}

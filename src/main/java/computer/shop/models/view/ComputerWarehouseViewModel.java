package computer.shop.models.view;

public class ComputerWarehouseViewModel {
    private Long id;
    private String name;
    private String description;
    private boolean published;

    public ComputerWarehouseViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ComputerWarehouseViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComputerWarehouseViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ComputerWarehouseViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isPublished() {
        return published;
    }

    public ComputerWarehouseViewModel setPublished(boolean published) {
        this.published = published;
        return this;
    }
}

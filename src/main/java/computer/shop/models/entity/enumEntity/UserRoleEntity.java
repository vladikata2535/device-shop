package computer.shop.models.entity.enumEntity;

import computer.shop.models.entity.BaseEntity;
import computer.shop.models.entity.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
    private UserRoleEnum role;

    public UserRoleEntity() {
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}

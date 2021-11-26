package computer.shop.repository;

import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findByRole(UserRoleEnum roleEnum);
}

package computer.shop.repository;

import computer.shop.models.entity.enumEntity.PowerSupplyEntity;
import computer.shop.models.entity.enums.componentsEnums.PowerSupplyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupplyEntity,Long> {
    PowerSupplyEntity findByName(PowerSupplyEnum name);
}

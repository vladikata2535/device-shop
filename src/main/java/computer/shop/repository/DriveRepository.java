package computer.shop.repository;

import computer.shop.models.entity.enumEntity.DriveEntity;
import computer.shop.models.entity.enums.componentsEnums.DriveEnum;
import computer.shop.models.entity.enums.componentsEnums.DriveTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveRepository extends JpaRepository<DriveEntity,Long> {
    DriveEntity findByName(DriveEnum name);
}

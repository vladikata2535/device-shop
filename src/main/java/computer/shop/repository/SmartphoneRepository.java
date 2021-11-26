package computer.shop.repository;

import computer.shop.models.entity.SmartphoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartphoneRepository extends JpaRepository<SmartphoneEntity,Long> {
    SmartphoneEntity findByModelName(String modelName);
}

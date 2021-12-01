package computer.shop.repository;

import computer.shop.models.entity.SmartphoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmartphoneRepository extends JpaRepository<SmartphoneEntity,Long> {
    Optional<SmartphoneEntity> findByModelName(String modelName);
}

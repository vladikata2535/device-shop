package computer.shop.repository;

import computer.shop.models.entity.SmartphoneOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmartphoneOfferRepository extends JpaRepository<SmartphoneOfferEntity, Long> {
    Optional<SmartphoneOfferEntity> findByName(String testSmartphoneOfferName);
}

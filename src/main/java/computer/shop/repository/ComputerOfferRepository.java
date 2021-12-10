package computer.shop.repository;

import computer.shop.models.entity.ComputerOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerOfferRepository extends JpaRepository<ComputerOfferEntity, Long> {
    Optional<ComputerOfferEntity> findByName(String testComputerOfferName);
}

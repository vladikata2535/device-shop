package computer.shop.repository;

import computer.shop.models.entity.ComputerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<ComputerEntity, Long> {
    Optional<ComputerEntity> findByName(String computerName);
}

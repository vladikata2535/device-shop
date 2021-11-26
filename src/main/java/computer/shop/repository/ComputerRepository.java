package computer.shop.repository;

import computer.shop.models.entity.ComputerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<ComputerEntity, Long> {
    ComputerEntity findByName(String computerName);
}

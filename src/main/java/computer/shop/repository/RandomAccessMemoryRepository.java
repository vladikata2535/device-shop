package computer.shop.repository;

import computer.shop.models.entity.enumEntity.RandomAccessMemoryEntity;
import computer.shop.models.entity.enums.componentsEnums.RandomAccessMemoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomAccessMemoryRepository extends JpaRepository<RandomAccessMemoryEntity,Long> {
    RandomAccessMemoryEntity findByName(RandomAccessMemoryEnum name);
}

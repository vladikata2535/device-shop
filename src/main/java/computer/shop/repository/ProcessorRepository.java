package computer.shop.repository;

import computer.shop.models.entity.enumEntity.ProcessorEntity;
import computer.shop.models.entity.enums.componentsEnums.ProcessorEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessorRepository extends JpaRepository<ProcessorEntity,Long> {
    ProcessorEntity findByName(ProcessorEnum name);
}

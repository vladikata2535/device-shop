package computer.shop.repository;

import computer.shop.models.entity.enumEntity.VideoCardEntity;
import computer.shop.models.entity.enums.componentsEnums.VideoCardEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCardRepository extends JpaRepository<VideoCardEntity, Long> {
    VideoCardEntity findByName(VideoCardEnum name);
}

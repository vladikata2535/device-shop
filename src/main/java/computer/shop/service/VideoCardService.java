package computer.shop.service;

import computer.shop.models.entity.enumEntity.VideoCardEntity;
import computer.shop.models.entity.enums.componentsEnums.VideoCardEnum;

public interface VideoCardService {
    void initializeVideoCards();

    VideoCardEntity findVideoCardByName(VideoCardEnum videoCardEnum);
}

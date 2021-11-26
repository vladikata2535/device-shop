package computer.shop.service;

import computer.shop.models.entity.enumEntity.RandomAccessMemoryEntity;
import computer.shop.models.entity.enums.componentsEnums.RandomAccessMemoryEnum;

public interface RandomAccessMemoryService {
    void initializeRAM();

    RandomAccessMemoryEntity findRamByName(RandomAccessMemoryEnum accessMemoryEnum);
}

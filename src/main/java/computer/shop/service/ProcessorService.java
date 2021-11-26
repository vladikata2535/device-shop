package computer.shop.service;

import computer.shop.models.entity.enumEntity.ProcessorEntity;
import computer.shop.models.entity.enums.componentsEnums.ProcessorEnum;

public interface ProcessorService {
    void initializeProcessors();

    ProcessorEntity findProcessorByName(ProcessorEnum processorEnum);
}

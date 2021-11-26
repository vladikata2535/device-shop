package computer.shop.service.impl;

import computer.shop.models.entity.enumEntity.ProcessorEntity;
import computer.shop.models.entity.enums.componentsEnums.ProcessorEnum;
import computer.shop.repository.ProcessorRepository;
import computer.shop.service.ProcessorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class ProcessorServiceImpl implements ProcessorService {

    private final ProcessorRepository processorRepository;

    public ProcessorServiceImpl(ProcessorRepository processorRepository) {
        this.processorRepository = processorRepository;
    }

    @Override
    public void initializeProcessors() {
        Arrays.stream(ProcessorEnum.values())
                .forEach(processorEnum -> {
                    ProcessorEntity processor = null;
                    switch (processorEnum) {
                        case AMD_Ryzen_7_2700X:
                            processor = new ProcessorEntity(processorEnum, "AM4", 105, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(329));
                            break;
                        case AMD_Ryzen_7_5800X:
                            processor = new ProcessorEntity(processorEnum, "AM4", 105, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(449));
                            break;
                        case AMD_Ryzen_9_5950X:
                            processor = new ProcessorEntity(processorEnum, "AM4", 105, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(799));
                            break;
                        case Intel_Pentium_G4560:
                            processor = new ProcessorEntity(processorEnum, "FCLGA1151", 54, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(64));
                            break;
                        case Intel_Core_i5_11600K:
                            processor = new ProcessorEntity(processorEnum, "FCLGA1200", 125, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(262));
                            break;
                        case Intel_Core_i7_10700K:
                            processor = new ProcessorEntity(processorEnum, "FCLGA1200", 125, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(374));
                            break;
                        case Intel_Core_i9_10900K:
                            processor = new ProcessorEntity(processorEnum, "FCLGA1200", 125, String.format("Description about processor with name: %s", processorEnum.name()), BigDecimal.valueOf(488));
                            break;
                    }
                    processorRepository.save(processor);
                });
    }

    @Override
    public ProcessorEntity findProcessorByName(ProcessorEnum processorEnum) {
        return processorRepository.findByName(processorEnum);
    }
}

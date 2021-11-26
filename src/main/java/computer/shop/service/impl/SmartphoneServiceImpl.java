package computer.shop.service.impl;

import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.service.SmartphoneServiceModel;
import computer.shop.models.view.SmartphoneAddOfferViewModel;
import computer.shop.models.view.SmartphoneWarehouseViewModel;
import computer.shop.repository.SmartphoneRepository;
import computer.shop.service.SmartphoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmartphoneServiceImpl implements SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;
    private final ModelMapper modelMapper;

    public SmartphoneServiceImpl(SmartphoneRepository smartphoneRepository, ModelMapper modelMapper) {
        this.smartphoneRepository = smartphoneRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addSmartphone(SmartphoneServiceModel serviceModel) {
        SmartphoneEntity smartphoneEntity = modelMapper.map(serviceModel, SmartphoneEntity.class);
        smartphoneEntity.setPublished(false);

        smartphoneRepository.save(smartphoneEntity);
    }

    @Override
    public List<SmartphoneAddOfferViewModel> getAllSmartphones() {
        return smartphoneRepository
                .findAll()
                .stream()
                .filter(smartphoneEntity -> !smartphoneEntity.isPublished())
                .map(smartphoneEntity -> modelMapper.map(smartphoneEntity, SmartphoneAddOfferViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public SmartphoneEntity findSmartphoneByModelName(String modelName) {
        return smartphoneRepository.findByModelName(modelName);
    }

    @Override
    public List<SmartphoneWarehouseViewModel> getAllSmartphonesWarehouse() {
        return smartphoneRepository
                .findAll()
                .stream()
                .map(smartphoneEntity -> {
                    SmartphoneWarehouseViewModel viewModel = modelMapper.map(smartphoneEntity, SmartphoneWarehouseViewModel.class);
                    viewModel.setPublished(smartphoneEntity.isPublished());

                    return viewModel;
                })
                .collect(Collectors.toList());
    }
}

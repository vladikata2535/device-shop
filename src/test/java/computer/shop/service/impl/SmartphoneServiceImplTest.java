package computer.shop.service.impl;

import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.service.SmartphoneServiceModel;
import computer.shop.models.view.SmartphoneAddOfferViewModel;
import computer.shop.models.view.SmartphoneWarehouseViewModel;
import computer.shop.repository.SmartphoneRepository;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SmartphoneServiceImplTest {

    private SmartphoneServiceImpl smartphoneService;

    @Mock
    private SmartphoneRepository smartphoneRepository;

    @Mock
    private ModelMapper modelMapper;

    private SmartphoneEntity smartphoneTest;

    @BeforeEach
    void setUp() {
        smartphoneService = new SmartphoneServiceImpl(smartphoneRepository, modelMapper);

        smartphoneTest = new SmartphoneEntity()
                .setPublished(false)
                .setDescription("DESCRIPTION")
                .setPrice(BigDecimal.valueOf(100))
                .setBackCameraMP(25)
                .setChipset("Chipset")
                .setBatteryCapacity(5000)
                .setCompanyName("Company")
                .setBuiltInMemory(6)
                .setDisplayInches(6.5)
                .setFrontCameraMP(25)
                .setManufacturedOn(LocalDate.now())
                .setModelName("Kosatka")
                .setOperationalSystem("Android")
                .setProcessor("Processor")
                .setRam(6);
    }

    @Test
    void TestAddSmartphone(){SmartphoneServiceModel serviceModel = new SmartphoneServiceModel()
            .setDescription("DESCRIPTION")
            .setPrice(BigDecimal.valueOf(100))
            .setBackCameraMP(25)
            .setChipset("Chipset")
            .setBatteryCapacity(5000)
            .setCompanyName("Company")
            .setBuiltInMemory(6)
            .setDisplayInches(6.5)
            .setFrontCameraMP(25)
            .setManufacturedOn(LocalDate.now())
            .setModelName("Kosatka")
            .setOperationalSystem("Android")
            .setProcessor("Processor")
            .setRam(6);

        Mockito.when(modelMapper.map(serviceModel, SmartphoneEntity.class))
                .thenReturn(smartphoneTest);
        Mockito.when(smartphoneRepository.save(smartphoneTest))
                .thenReturn(smartphoneTest);

        smartphoneService.addSmartphone(serviceModel);

        Assertions.assertEquals(serviceModel.getModelName(), smartphoneTest.getModelName());
        Assertions.assertEquals(serviceModel.getCompanyName(), smartphoneTest.getCompanyName());
    }

    @Test
    void TestGetAllSmartphonesWarehouse(){
        SmartphoneWarehouseViewModel smartphoneWarehouseViewModel = new SmartphoneWarehouseViewModel().setPublished(smartphoneTest.isPublished()).setDescription(smartphoneTest.getDescription()).setModelName(smartphoneTest.getModelName());

        Mockito.when(smartphoneRepository.findAll())
                .thenReturn(List.of(smartphoneTest));
        Mockito.when(modelMapper.map(smartphoneTest, SmartphoneWarehouseViewModel.class))
                .thenReturn(smartphoneWarehouseViewModel);

        List<SmartphoneWarehouseViewModel> allSmartphonesWarehouse = smartphoneService.getAllSmartphonesWarehouse();

        Assertions.assertEquals(List.of(smartphoneWarehouseViewModel), allSmartphonesWarehouse);
    }

    @Test
    void TestGetAllSmartphones(){
        SmartphoneAddOfferViewModel viewModel = new SmartphoneAddOfferViewModel().setModelName(smartphoneTest.getModelName());
        Mockito.when(smartphoneRepository.findAll())
                .thenReturn(List.of(smartphoneTest));
        Mockito.when(modelMapper.map(smartphoneTest, SmartphoneAddOfferViewModel.class))
                .thenReturn(viewModel);

        List<SmartphoneAddOfferViewModel> smartphones = smartphoneService.getAllSmartphones();

        Assertions.assertEquals(List.of(viewModel), smartphones);
    }

    @Test
    void TestFindSmartphoneByModelName(){
        Mockito.when(smartphoneRepository.findByModelName(smartphoneTest.getModelName()))
                .thenReturn(Optional.of(smartphoneTest));

        SmartphoneEntity modelName = smartphoneService.findSmartphoneByModelName(smartphoneTest.getModelName());

        Assertions.assertEquals(smartphoneTest, modelName);
    }

    @Test
    void TestFindSmartphoneByModelNameWithError(){
        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> smartphoneService.findSmartphoneByModelName(smartphoneTest.getModelName())
        );
    }
}
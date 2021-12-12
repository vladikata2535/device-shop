package computer.shop.service.impl;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;
import computer.shop.models.entity.enums.componentsEnums.*;
import computer.shop.models.service.ComputerServiceModel;
import computer.shop.models.view.ComputerAddOfferViewModel;
import computer.shop.models.view.ComputerWarehouseViewModel;
import computer.shop.repository.ComputerOfferRepository;
import computer.shop.repository.ComputerRepository;
import computer.shop.service.*;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComputerServiceImplTest {

    private ComputerServiceImpl computerService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ComputerRepository computerRepository;

    @Mock
    private ComputerOfferRepository computerOfferRepository;

    @Mock
    private DriveService driveService;

    @Mock
    private PowerSupplyService powerSupplyService;

    @Mock
    private ProcessorService processorService;

    @Mock
    private RandomAccessMemoryService randomAccessMemoryService;

    @Mock
    private VideoCardService videoCardService;

    private ComputerEntity computerTest;

    @BeforeEach
    void setUp() {

        computerService = new ComputerServiceImpl(modelMapper, computerRepository, computerOfferRepository, driveService, powerSupplyService, processorService, randomAccessMemoryService, videoCardService);

        VideoCardEntity videoCard = new VideoCardEntity()
                .setDescription("random text")
                .setGpuCores(500)
                .setMemory("Memory")
                .setName(VideoCardEnum.AMD_Radeon_RX_5700)
                .setPrice(BigDecimal.valueOf(100));

        ProcessorEntity processor = new ProcessorEntity()
                .setTDP(100)
                .setDescription("random text")
                .setName(ProcessorEnum.AMD_Ryzen_7_2700X)
                .setPrice(BigDecimal.valueOf(100))
                .setSocket("Socket");

        RandomAccessMemoryEntity randomAccessMemoryEntity = new RandomAccessMemoryEntity()
                .setDIMMs("DIMMS")
                .setDescription("random text")
                .setName(RandomAccessMemoryEnum.Colorful_CVN_Guardian_16GB_DDR4_3200)
                .setPrice(BigDecimal.valueOf(100))
                .setSpeed("Fast Speed")
                .setVoltage(250);

        PowerSupplyEntity powerSupply = new PowerSupplyEntity()
                .setPCIeConnectors(5)
                .setCooling("Cooling")
                .setDescription("random text")
                .setEfficiency("Efficiency")
                .setMaxDcOutput(50)
                .setName(PowerSupplyEnum.Corsair_AX1600i)
                .setNoise("Noise")
                .setPrice(BigDecimal.valueOf(100))
                .setWarranty(5);

        DriveEntity drive = new DriveEntity()
                .setCapacity("Capacity")
                .setDriveType(DriveTypeEnum.SOLID_STATE_DRIVE)
                .setName(DriveEnum.Crucial_P5_Plus)
                .setPrice(BigDecimal.valueOf(100))
                .setWarranty(5);

        computerTest = new ComputerEntity()
                .setPublished(false)
                .setDrive(drive)
                .setPrice(BigDecimal.valueOf(100))
                .setPowerSupply(powerSupply)
                .setProcessor(processor)
                .setRam(randomAccessMemoryEntity)
                .setVideoCard(videoCard)
                .setName("Killer")
                .setManufacturedOn(LocalDate.now())
                .setDescription("Description")
                .setComputerBox(ComputerBoxEnum.MID_TOWER);
    }

    @Test
    void TestAddComputer(){
        ComputerServiceModel serviceModel = new ComputerServiceModel()
                .setComputerBox(computerTest.getComputerBox())
                .setDrive(computerTest.getDrive().getName())
                .setPowerSupply(computerTest.getPowerSupply().getName())
                .setDescription(computerTest.getDescription())
                .setProcessor(computerTest.getProcessor().getName())
                .setManufacturedOn(computerTest.getManufacturedOn())
                .setName(computerTest.getName())
                .setPrice(computerTest.getPrice())
                .setRam(computerTest.getRam().getName())
                .setVideoCard(computerTest.getVideoCard().getName());

        Mockito.when(modelMapper.map(serviceModel, ComputerEntity.class))
                .thenReturn(computerTest);
        Mockito.when(driveService.findDriveByName(serviceModel.getDrive()))
                .thenReturn(computerTest.getDrive());
        Mockito.when(powerSupplyService.findPowerSupplyByName(serviceModel.getPowerSupply()))
                .thenReturn(computerTest.getPowerSupply());
        Mockito.when(processorService.findProcessorByName(serviceModel.getProcessor()))
                .thenReturn(computerTest.getProcessor());
        Mockito.when(randomAccessMemoryService.findRamByName(serviceModel.getRam()))
                .thenReturn(computerTest.getRam());
        Mockito.when(videoCardService.findVideoCardByName(serviceModel.getVideoCard()))
                .thenReturn(computerTest.getVideoCard());
        Mockito.when(computerRepository.save(computerTest))
                .thenReturn(computerTest);

        computerService.addComputer(serviceModel);

        Assertions.assertEquals(serviceModel.getName(), computerTest.getName());
        Assertions.assertEquals(BigDecimal.valueOf(500), computerTest.getPrice());
    }

    @Test
    void TestGetAllComputersWarehouse(){
        ComputerWarehouseViewModel viewModel = new ComputerWarehouseViewModel().setName(computerTest.getName()).setPublished(computerTest.isPublished()).setDescription(computerTest.getDescription());
        Mockito.when(computerRepository.findAll())
                .thenReturn(List.of(computerTest));
        Mockito.when(modelMapper.map(computerTest, ComputerWarehouseViewModel.class))
                .thenReturn(viewModel);

        List<ComputerWarehouseViewModel> computers = computerService.getAllComputersWarehouse();

        Assertions.assertEquals(List.of(viewModel), computers);
    }

    @Test
    void getAllComputers(){
        ComputerAddOfferViewModel computerAddOfferViewModel = new ComputerAddOfferViewModel().setName(computerTest.getName());
        Mockito.when(computerRepository.findAll())
                .thenReturn(List.of(computerTest));
        Mockito.when(modelMapper.map(computerTest, ComputerAddOfferViewModel.class))
                .thenReturn(computerAddOfferViewModel);

        List<ComputerAddOfferViewModel> computersToTest = computerService.getAllComputers();

        Assertions.assertEquals(List.of(computerAddOfferViewModel), computersToTest);
    }

    @Test
    void TestFindComputerByName(){
        Mockito.when(computerRepository.findByName(computerTest.getName()))
                .thenReturn(Optional.of(computerTest));

        ComputerEntity computer = computerService.findComputerByName(computerTest.getName());

        Assertions.assertEquals(computerTest, computer);
    }

    @Test
    void TestFindComputerByNameWithError(){
        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> computerService.findComputerByName(computerTest.getName())
        );
    }
}
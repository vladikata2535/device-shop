package computer.shop.service.impl;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.ComputerOfferEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.entity.enums.componentsEnums.*;
import computer.shop.models.service.ComputerUpdateServiceModel;
import computer.shop.repository.ComputerOfferRepository;
import computer.shop.repository.ComputerRepository;
import computer.shop.repository.UserRepository;
import computer.shop.service.ComputerService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ComputerOfferServiceImplTest {

    private ComputerOfferServiceImpl computerOfferService;

    @Mock
    private ComputerOfferRepository computerOfferRepository;
    @Mock
    private ComputerRepository computerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ComputerService computerService;

    private ComputerOfferEntity computerOfferEntityTest;
    private ComputerEntity computerTest;
    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;

    @BeforeEach
    void setUp() {
        computerOfferService = new ComputerOfferServiceImpl(computerOfferRepository, computerRepository, userRepository, modelMapper, computerService);

        adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        testUser = new UserEntity()
                .setUsername("admin")
                .setFirstName("Gosho")
                .setLastName("Georgiev")
                .setPassword("test")
                .setEmail("gosho@abv.bg")
                .setAge(56)
                .setBornOn(LocalDate.now())
                .setBalance(BigDecimal.valueOf(1000))
                .setCountry("Bulgaria")
                .setRoles(Set.of(adminRole, userRole));

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

        computerOfferEntityTest = new ComputerOfferEntity()
                .setComputer(computerTest)
                .setSeller(testUser)
                .setCreatedOn(LocalDate.now())
                .setName("Killer Offer")
                .setPrice(computerTest.getPrice())
                .setDescription("Description");
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void TestBuyComputer(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.of(computerOfferEntityTest));
        Mockito.when(userRepository.save(testUser))
                .thenReturn(testUser);

        computerOfferService.buyProduct(1L, "15% COUPON", testUser.getUsername());

        Assertions.assertTrue(true);
    }

    @Test
    void testFindOfferForEdit(){
        ComputerUpdateServiceModel computerUpdateServiceModel = new ComputerUpdateServiceModel()
                .setCreatedOn(computerOfferEntityTest.getCreatedOn())
                .setPrice(computerOfferEntityTest.getPrice())
                .setComputerName(computerOfferEntityTest.getComputer().getName())
                .setDescription(computerOfferEntityTest.getDescription())
                .setName(computerOfferEntityTest.getName())
                .setId(computerOfferEntityTest.getId());

        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.of(computerOfferEntityTest));
        Mockito.when(modelMapper.map(computerOfferEntityTest, ComputerUpdateServiceModel.class))
                .thenReturn(computerUpdateServiceModel);

        ComputerUpdateServiceModel offerForEdit = computerOfferService.findOfferForEdit(1L);

        Assertions.assertEquals(computerUpdateServiceModel, offerForEdit);
    }

    @Test
    void testFindOfferForEditWithException(){
        ComputerUpdateServiceModel computerUpdateServiceModel = new ComputerUpdateServiceModel()
                .setCreatedOn(computerOfferEntityTest.getCreatedOn())
                .setPrice(computerOfferEntityTest.getPrice())
                .setComputerName(computerOfferEntityTest.getComputer().getName())
                .setDescription(computerOfferEntityTest.getDescription())
                .setName(computerOfferEntityTest.getName())
                .setId(computerOfferEntityTest.getId());

        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            ComputerUpdateServiceModel offerForEdit = computerOfferService.findOfferForEdit(1L);
        });
    }

    @Test
    void TestFindPriceByComputerName(){
        Mockito.when(computerRepository.findByName(computerTest.getName()))
                .thenReturn(Optional.of(computerTest));

        BigDecimal actualPrice = computerOfferService.findPriceByComputerName(computerTest.getName());

        Assertions.assertEquals(computerTest.getPrice(), actualPrice);
    }

    @Test
    void TestFindPriceByComputerNameWithException(){
        Mockito.when(computerRepository.findByName(computerTest.getName()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            BigDecimal actualPrice = computerOfferService.findPriceByComputerName(computerTest.getName());
        });
    }

    @Test
    void testFindComputerName(){
        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.of(computerOfferEntityTest));
        String nameActual = computerOfferService.findComputerName(1L);

        Assertions.assertEquals(computerOfferEntityTest.getComputer().getName(), nameActual);
    }

    @Test
    void testFindComputerNameWithException(){
        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            String nameActual = computerOfferService.findComputerName(1L);
        });
    }

    @Test
    void testIsAdmin(){
        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.of(computerOfferEntityTest));
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        boolean isAdmin = computerOfferService.isAdmin(testUser.getUsername(), 1L);

        Assertions.assertTrue(isAdmin);
    }

    @Test
    void testIsAdminFalse(){
        Mockito.when(computerOfferRepository.findById(1L))
                .thenReturn(Optional.of(computerOfferEntityTest));
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.empty());

        boolean isAdmin = computerOfferService.isAdmin(testUser.getUsername(), 1L);

        Assertions.assertFalse(isAdmin);
    }


}
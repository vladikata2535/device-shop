package computer.shop.service.impl;

import computer.shop.models.dto.SmartphoneDTO;
import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.entity.SmartphoneOfferEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.models.view.SmartphoneOfferDetailsView;
import computer.shop.models.view.SmartphoneOfferViewModel;
import computer.shop.repository.SmartphoneOfferRepository;
import computer.shop.repository.SmartphoneRepository;
import computer.shop.repository.UserRepository;
import computer.shop.service.SmartphoneService;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SmartphoneOfferServiceImplTest {

    private SmartphoneOfferServiceImpl smartphoneOfferService;

    @Mock
    private SmartphoneOfferRepository smartphoneOfferRepository;
    @Mock
    private SmartphoneRepository smartphoneRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SmartphoneService smartphoneService;
    @Mock
    private UserRepository userRepository;

    private SmartphoneOfferEntity smartphoneOfferEntityTest;
    private SmartphoneEntity smartphone;
    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;

    @BeforeEach
    void setUp() {
        smartphoneOfferService = new SmartphoneOfferServiceImpl(smartphoneOfferRepository, smartphoneRepository, modelMapper, smartphoneService, userRepository);

        smartphone = new SmartphoneEntity();

        smartphone
                .setBackCameraMP(15)
                .setBatteryCapacity(5000)
                .setBuiltInMemory(125)
                .setChipset("Chipset")
                .setCompanyName("Company name")
                .setDescription("Description")
                .setDisplayInches(6.0)
                .setFrontCameraMP(15)
                .setManufacturedOn(LocalDate.now())
                .setModelName("Model name one")
                .setOperationalSystem("Android")
                .setPrice(BigDecimal.valueOf(100))
                .setProcessor("Processor")
                .setPublished(false)
                .setRam(4);

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

        smartphoneOfferEntityTest = new SmartphoneOfferEntity()
                .setSmartphone(smartphone)
                .setDescription("Description")
                .setName("Killer Offer")
                .setSeller(testUser)
                .setPrice(smartphone.getPrice())
                .setCreatedOn(LocalDate.now());

    }

    @Test
    void TestGetAllSmartphonesCatalog() {
        SmartphoneOfferViewModel viewModel = new SmartphoneOfferViewModel()
                .setModelName(smartphoneOfferEntityTest.getSmartphone().getModelName())
                .setPrice(smartphoneOfferEntityTest.getPrice())
                .setCreatedOn(smartphoneOfferEntityTest.getCreatedOn())
                .setName(smartphoneOfferEntityTest.getName())
                .setReviews((long)100);

        Mockito.when(smartphoneOfferRepository.findAll())
                .thenReturn(List.of(smartphoneOfferEntityTest));
        Mockito.when(modelMapper.map(smartphoneOfferEntityTest, SmartphoneOfferViewModel.class))
                .thenReturn(viewModel);

        List<SmartphoneOfferViewModel> catalog = smartphoneOfferService.getAllSmartphonesCatalog();

        Assertions.assertEquals(List.of(viewModel), catalog);
    }

    @Test
    void TestFindOfferById(){
        Long id = 1L;
        SmartphoneOfferDetailsView view = new SmartphoneOfferDetailsView()
                .setName(smartphoneOfferEntityTest.getName())
                .setSmartphone(new SmartphoneDTO())
                .setPrice(smartphoneOfferEntityTest.getPrice())
                .setSellerFullName(smartphoneOfferEntityTest.getSeller().getFirstName() + " " + smartphoneOfferEntityTest.getSeller().getLastName())
                .setId(id);

        Mockito.when(smartphoneOfferRepository.findById(id))
                .thenReturn(Optional.of(smartphoneOfferEntityTest));
        Mockito.when(modelMapper.map(smartphoneOfferEntityTest, SmartphoneOfferDetailsView.class))
                .thenReturn(view);

        SmartphoneOfferDetailsView offerById = smartphoneOfferService.findOfferById(id);

        Assertions.assertEquals(view, offerById);
    }

    @Test
    void TestFindOfferPriceById(){
        Mockito.when(smartphoneOfferRepository.findById(1L))
                .thenReturn(Optional.of(smartphoneOfferEntityTest));
        double price = smartphoneOfferService.findOfferPriceById(1L);

        Assertions.assertEquals(smartphoneOfferEntityTest.getPrice().doubleValue(), price);
    }

    @Test
    void TestFindSmartphoneName(){
        Mockito.when(smartphoneOfferRepository.findById(1L))
                .thenReturn(Optional.of(smartphoneOfferEntityTest));

        String name = smartphoneOfferService.findSmartphoneName(1L);

        Assertions.assertEquals(smartphoneOfferEntityTest.getSmartphone().getModelName(), name);
    }

    @Test
    void TestFindPriceBySmartphoneName(){
        Mockito.when(smartphoneRepository.findByModelName(smartphoneOfferEntityTest.getSmartphone().getModelName()))
                .thenReturn(Optional.of(smartphone));

        BigDecimal price = smartphoneOfferService.findPriceBySmartphoneName(smartphoneOfferEntityTest.getSmartphone().getModelName());

        Assertions.assertEquals(smartphoneOfferEntityTest.getSmartphone().getPrice(), price);
    }

    @Test
    void TestIsAdmin(){
        Mockito.when(smartphoneOfferRepository.findById(1L))
                .thenReturn(Optional.of(smartphoneOfferEntityTest));
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        boolean isAdmin = smartphoneOfferService.isAdmin(testUser.getUsername(), 1L);

        Assertions.assertTrue(isAdmin);
    }

    @Test
    void TestIsNotAdmin(){
        Mockito.when(smartphoneOfferRepository.findById(1L))
                .thenReturn(Optional.of(smartphoneOfferEntityTest));
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.empty());

        boolean isAdmin = smartphoneOfferService.isAdmin(testUser.getUsername(), 1L);

        Assertions.assertFalse(isAdmin);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void TestDeleteOffer(){
        Mockito.when(smartphoneOfferRepository.findById(1L))
                .thenReturn(Optional.of(smartphoneOfferEntityTest));
        Mockito.when(smartphoneRepository.findById(smartphoneOfferEntityTest.getSmartphone().getId()))
                .thenReturn(Optional.of(smartphoneOfferEntityTest.getSmartphone()));
        Mockito.when(smartphoneRepository.save(smartphoneOfferEntityTest.getSmartphone()))
                .thenReturn(smartphone);

        smartphoneOfferService.deleteOffer(1L);

        Assertions.assertTrue(true);
    }

    @Test
    void TestAddOffer(){
        SmartphoneOfferServiceModel serviceModel = new SmartphoneOfferServiceModel().setSmartphone(smartphoneOfferEntityTest.getSmartphone().getModelName());
        Mockito.when(smartphoneService.findSmartphoneByModelName(smartphoneOfferEntityTest.getSmartphone().getModelName()))
                .thenReturn(smartphoneOfferEntityTest.getSmartphone());
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(modelMapper.map(serviceModel, SmartphoneOfferEntity.class))
                .thenReturn(smartphoneOfferEntityTest);
        Mockito.when(smartphoneRepository.save(smartphone))
                .thenReturn(smartphoneOfferEntityTest.getSmartphone());
        Mockito.when(smartphoneOfferRepository.save(smartphoneOfferEntityTest))
                .thenReturn(smartphoneOfferEntityTest);

        smartphoneOfferService.addSmartphoneOffer(serviceModel, testUser.getUsername());

        Assertions.assertEquals(serviceModel.getSmartphone(), smartphoneOfferEntityTest.getSmartphone().getModelName());
    }
}
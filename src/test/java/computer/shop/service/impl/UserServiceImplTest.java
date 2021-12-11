package computer.shop.service.impl;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.service.UserServiceModel;
import computer.shop.models.view.UserBalanceViewModel;
import computer.shop.models.view.UserViewModel;
import computer.shop.repository.CouponRepository;
import computer.shop.repository.UserRepository;
import computer.shop.repository.UserRoleRepository;
import computer.shop.service.ComputerOfferService;
import computer.shop.service.SmartphoneOfferService;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private DeviceShopUserServiceImpl deviceShopUserService;

    @Mock
    private ComputerOfferService computerOfferService;

    @Mock
    private SmartphoneOfferService smartphoneOfferService;

    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;
    private CouponEntity couponEntity;
    private UserServiceModel userServiceModel;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userRoleRepository, modelMapper, passwordEncoder, couponRepository, deviceShopUserService, computerOfferService, smartphoneOfferService);

        adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        testUser = new UserEntity()
                .setUsername("gosho")
                .setFirstName("Gosho")
                .setLastName("Georgiev")
                .setPassword("test")
                .setEmail("gosho@abv.bg")
                .setAge(56)
                .setBornOn(LocalDate.now())
                .setBalance(BigDecimal.valueOf(1000))
                .setCountry("Bulgaria")
                .setRoles(Set.of(adminRole,userRole));

        couponEntity = new CouponEntity()
                .setDescription("DESCRIPTION COUPON")
                .setActivated(false)
                .setPercentage(CouponPercentageEnum.fifteen);

        userServiceModel = new UserServiceModel()
                .setAge(testUser.getAge())
                .setBornOn(testUser.getBornOn())
                .setCountry(testUser.getCountry())
                .setEmail(testUser.getEmail())
                .setFirstName(testUser.getFirstName())
                .setLastName(testUser.getLastName())
                .setPassword(testUser.getPassword())
                .setUsername(testUser.getUsername());


    }

    @Test
    void TestRegisterUserMethod(){
        List<GrantedAuthority> grantedAuthorities =
                testUser
                        .getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                        .collect(Collectors.toList());

        Mockito.when(userRoleRepository.findByRole(UserRoleEnum.USER))
                .thenReturn(userRole);
        Mockito.when(couponRepository.findByPercentage(CouponPercentageEnum.fifteen))
                .thenReturn(couponEntity);
        Mockito.when(modelMapper.map(userServiceModel, UserEntity.class))
                .thenReturn(testUser);
        Mockito.when(passwordEncoder.encode(testUser.getPassword()))
                .thenReturn(testUser.getPassword());
        Mockito.when(deviceShopUserService.loadUserByUsername(testUser.getUsername()))
                .thenReturn(new User(testUser.getUsername(), testUser.getPassword(), grantedAuthorities));
        Mockito.when(modelMapper.map(testUser, UserServiceModel.class))
                        .thenReturn(userServiceModel);
        Mockito.when(userRepository.save(testUser))
                        .thenReturn(testUser);
        userService.registerUser(modelMapper.map(testUser, UserServiceModel.class));

        Assertions.assertFalse(SecurityContextHolder.getContext().getAuthentication().getAuthorities().isEmpty());
    }

    @Test
    void TestFindUserByUsername(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        Assertions.assertEquals(Optional.of(testUser), userService.findUserByName(testUser.getUsername()));
    }

    @Test
    void TestGetLoggedUserInfo(){
        UserBalanceViewModel userBalanceViewModel = new UserBalanceViewModel();
        userBalanceViewModel.setBalance(testUser.getBalance());

        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(modelMapper.map(testUser, UserBalanceViewModel.class))
                .thenReturn(userBalanceViewModel);

        UserBalanceViewModel userInfo = userService.getLoggedUserInfo(testUser.getUsername());

        Assertions.assertEquals(userBalanceViewModel, userInfo);
    }

    @Test
    void TestIsUserExistingByEmailOrUsername(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));
        Assertions.assertTrue(userService.isUserExistingByEmailOrUsername(testUser.getEmail(), testUser.getUsername()));
    }

    @Test
    void TestIsUserExistingByEmail(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));
        Assertions.assertTrue(userService.isUserExistingByEmailOrUsername(testUser.getEmail(), testUser.getUsername()));
    }

    @Test
    void TestIsUserExistingByUsername(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.empty());
        Assertions.assertTrue(userService.isUserExistingByEmailOrUsername(testUser.getEmail(), testUser.getUsername()));
    }

    @Test
    void TestIsUserNotExisting(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.empty());
        Assertions.assertFalse(userService.isUserExistingByEmailOrUsername(testUser.getEmail(), testUser.getUsername()));
    }

    @Test
    void TestCanUserBuyComputerProduct(){
        Long offerId = (long)1;
        double price = 100.0;
        Mockito.when(computerOfferService.findOfferPriceById(offerId))
                .thenReturn(price);
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        boolean result = userService.canUserBuyProduct(null, offerId, "15% COUPON", testUser.getUsername());

        Assertions.assertTrue(result);
    }

    @Test
    void TestCanUserBuySmartphoneProduct(){
        Long offerId = (long)1;
        double price = 100.0;
        Mockito.when(smartphoneOfferService.findOfferPriceById(offerId))
                .thenReturn(price);
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        boolean result = userService.canUserBuyProduct(offerId, null, "15% COUPON", testUser.getUsername());

        Assertions.assertTrue(result);
    }

    @Test
    void TestCanUserNotBuySmartphoneProduct(){
        Long offerId = (long)1;
        double price = 10000.0;
        Mockito.when(smartphoneOfferService.findOfferPriceById(offerId))
                .thenReturn(price);
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        boolean result = userService.canUserBuyProduct(offerId, null, "15% COUPON", testUser.getUsername());

        Assertions.assertFalse(result);
    }

    @Test
    void TestCanUserNotBuyComputerProduct(){
        Long offerId = (long)1;
        double price = 10000.0;
        Mockito.when(computerOfferService.findOfferPriceById(offerId))
                .thenReturn(price);
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        boolean result = userService.canUserBuyProduct(null, offerId, "15% COUPON", testUser.getUsername());

        Assertions.assertFalse(result);
    }

    @Test
    void TestGetUserBalance(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        double priceForTest = userService.getUserBalance(testUser.getUsername());
        Assertions.assertEquals(testUser.getBalance().doubleValue(), priceForTest);
    }

    @Test
    void TestGetUserInfo(){
        UserViewModel actual = new UserViewModel().setAge(testUser.getAge()).setUsername(testUser.getUsername()).setEmail(testUser.getEmail()).setBornOn(testUser.getBornOn()).setFirstName(testUser.getFirstName()).setLastName(testUser.getLastName()).setCountry(testUser.getCountry());
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(modelMapper.map(testUser, UserViewModel.class))
                .thenReturn(actual);
        UserViewModel userInfoTest = userService.getUserInfo(testUser.getUsername());

        Assertions.assertEquals(actual.getAge(), userInfoTest.getAge());
        Assertions.assertEquals(actual.getUsername(), userInfoTest.getUsername());
        Assertions.assertEquals(actual.getEmail(), userInfoTest.getEmail());
        Assertions.assertEquals(actual.getBornOn(), userInfoTest.getBornOn());
        Assertions.assertEquals(actual.getFirstName(), userInfoTest.getFirstName());
        Assertions.assertEquals(actual.getLastName(), userInfoTest.getLastName());
        Assertions.assertEquals(actual.getCountry(), userInfoTest.getCountry());
    }

    @Test
    void TestAddMoneyToUser(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.save(testUser))
                .thenReturn(testUser);
        userService.addMoneyToUser(testUser.getUsername());

        Assertions.assertEquals(2000, testUser.getBalance().doubleValue());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void TestInitializeUsers(){
        Mockito.when(userRoleRepository.findByRole(UserRoleEnum.USER))
                .thenReturn(userRole);
        Mockito.when(userRoleRepository.findByRole(UserRoleEnum.ADMIN))
                .thenReturn(adminRole);
        Mockito.when(couponRepository.findByPercentage(CouponPercentageEnum.fifty))
                .thenReturn(new CouponEntity().setActivated(false).setDescription("DESCRIPTION").setPercentage(CouponPercentageEnum.fifty));
        Mockito.when(couponRepository.findByPercentage(CouponPercentageEnum.ten))
                .thenReturn(new CouponEntity().setActivated(false).setDescription("DESCRIPTION").setPercentage(CouponPercentageEnum.ten));
        Mockito.when(passwordEncoder.encode(testUser.getPassword()))
                .thenReturn(testUser.getPassword());
        Mockito.when(userRepository.save(testUser))
                .thenReturn(testUser);

        userService.initializeUsers();
    }
}
package computer.shop.service.impl;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.view.CouponViewModel;
import computer.shop.repository.CouponRepository;
import computer.shop.repository.UserRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    private CouponServiceImpl couponServiceTest;

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private CouponEntity couponTest;
    private CouponEntity couponTestSecond;
    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;
    private Set<CouponEntity> activeTest;
    private Set<CouponEntity> notActiveTest;

    @BeforeEach
    void setUp() {
        couponServiceTest = new CouponServiceImpl(couponRepository, userRepository, modelMapper);

        couponTest = new CouponEntity()
                .setDescription("Description")
                .setActivated(false)
                .setPercentage(CouponPercentageEnum.fifty);

        couponTestSecond = new CouponEntity()
                .setDescription("DescriptionTwo")
                .setActivated(false)
                .setPercentage(CouponPercentageEnum.fifteen);


        activeTest = new LinkedHashSet<>();
        activeTest.add(couponTest);
        notActiveTest = new LinkedHashSet<>();
        notActiveTest.add(couponTestSecond);

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
                .setRoles(Set.of(adminRole, userRole))
                .setNotActiveCoupons(notActiveTest)
                .setActiveCoupons(activeTest);
    }

    @Test

    void testFindAllCurrentLoggedUserNotActiveCoupons(){
        CouponViewModel couponViewModel = new CouponViewModel()
                .setDescription(couponTestSecond.getDescription())
                .setPercentageNumber(couponTestSecond.getPercentage().label)
                .setId(1L);
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(modelMapper.map(couponTestSecond, CouponViewModel.class))
                .thenReturn(couponViewModel);

        List<CouponViewModel> notActiveCoupons = couponServiceTest.findAllCurrentLoggedUserNotActiveCoupons(testUser.getUsername());

        Assertions.assertEquals(List.of(couponViewModel), notActiveCoupons);
    }

    @Test
    void testFindAllCurrentLoggedUserActiveCoupons(){
        CouponViewModel couponViewModel = new CouponViewModel()
                .setDescription(couponTest.getDescription())
                .setPercentageNumber(couponTest.getPercentage().label)
                .setId(1L);
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(modelMapper.map(couponTest, CouponViewModel.class))
                .thenReturn(couponViewModel);

        List<CouponViewModel> activeCoupons = couponServiceTest.findAllCurrentLoggedUserActiveCoupons(testUser.getUsername());

        Assertions.assertEquals(List.of(couponViewModel), activeCoupons);
    }

    @Test
    void testActivateCoupon(){
        Mockito.when(couponRepository.findById(1L))
                .thenReturn(Optional.of(couponTest));
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.save(testUser))
                .thenReturn(testUser);

        couponServiceTest.activateCoupon(1L, testUser.getUsername());

        Assertions.assertTrue(true);

    }


}
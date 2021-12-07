package computer.shop.service.impl;

import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class DeviceShopUserServiceImplTest {

    private DeviceShopUserServiceImpl deviceShopUserServiceToTest;
    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init(){
        deviceShopUserServiceToTest = new DeviceShopUserServiceImpl(mockUserRepository);

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
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> deviceShopUserServiceToTest.loadUserByUsername("WrongUsername")
        );
    }

    @Test
    void testUserFound(){
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        var actual = deviceShopUserServiceToTest.loadUserByUsername(testUser.getUsername());

        String actualRoles = actual
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        String expectedRoles = "ROLE_ADMIN, ROLE_USER";

        Assertions.assertEquals(actual.getUsername(), testUser.getUsername());
        Assertions.assertEquals(expectedRoles, actualRoles);
    }

}

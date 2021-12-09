package computer.shop.web;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.repository.CouponRepository;
import computer.shop.repository.UserRepository;
import computer.shop.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CouponRepository couponRepository;

    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;
    private CouponEntity couponEntity;

    @BeforeEach
    void setUp() {
        couponEntity = new CouponEntity();
        couponEntity.setActivated(false);
        couponEntity.setDescription("Coupon description.");
        couponEntity.setPercentage(CouponPercentageEnum.fifteen);
        couponEntity = couponRepository.save(couponEntity);

        adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        adminRole = userRoleRepository.save(adminRole);
        userRole = userRoleRepository.save(userRole);

        testUser = new UserEntity()
                .setUsername("admin")
                .setFirstName("Gosho")
                .setLastName("Georgiev")
                .setPassword("test")
                .setEmail("admin@abv.bg")
                .setAge(56)
                .setBornOn(LocalDate.now())
                .setBalance(BigDecimal.valueOf(1000))
                .setCountry("Bulgaria")
                .setRoles(Set.of(adminRole,userRole))
                .setNotActiveCoupons(Set.of(couponEntity));

        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        couponRepository.deleteAll();
    }

    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc
                .perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    private static final String TEST_USER_USERNAME = "pesho";
    private static final String TEST_USER_FIRST_NAME = "Pesho";
    private static final String TEST_USER_LAST_NAME = "Peshov";
    private static final String TEST_USER_EMAIL = "pesho@abv.bg";
    private static final Integer TEST_USER_AGE = 50;
    private static final LocalDate TEST_USER_BORN_ON = LocalDate.now();
    private static final String TEST_USER_COUNTRY = "Bulgaria";
    private static final String TEST_USER_PASSWORD = "12345";
    private static final String TEST_USER_CONFIRM_PASSWORD = "12345";

    private static final String TEST_USER_WRONG_USERNAME = "A";

    @Test
    void testRegisterUser() throws Exception {

        mockMvc.perform(post("/users/register")
                .param("username", TEST_USER_USERNAME)
                .param("firstName", TEST_USER_FIRST_NAME)
                .param("lastName", TEST_USER_LAST_NAME)
                .param("email", TEST_USER_EMAIL)
                .param("age", String.valueOf(TEST_USER_AGE))
                .param("bornOn", String.valueOf(TEST_USER_BORN_ON))
                .param("country", TEST_USER_COUNTRY)
                .param("password", TEST_USER_PASSWORD)
                .param("confirmPassword", TEST_USER_CONFIRM_PASSWORD)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Assertions.assertEquals(2, userRepository.count());

        Optional<UserEntity> newUserTestOpt = userRepository.findByUsername(TEST_USER_USERNAME);

        Assertions.assertTrue(newUserTestOpt.isPresent());

        UserEntity newUserTest = newUserTestOpt.get();

        Assertions.assertEquals(TEST_USER_USERNAME, newUserTest.getUsername());
        Assertions.assertEquals(TEST_USER_FIRST_NAME, newUserTest.getFirstName());
        Assertions.assertEquals(TEST_USER_LAST_NAME, newUserTest.getLastName());
        Assertions.assertEquals(TEST_USER_EMAIL, newUserTest.getEmail());
        Assertions.assertEquals(TEST_USER_AGE, newUserTest.getAge());
        Assertions.assertEquals(TEST_USER_BORN_ON, newUserTest.getBornOn());
        Assertions.assertEquals(TEST_USER_COUNTRY, newUserTest.getCountry());
        Assertions.assertTrue(passwordEncoder.matches(TEST_USER_CONFIRM_PASSWORD, newUserTest.getPassword()));
    }

    @Test
    void testRegisterUserWithWrongInfo() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("username", TEST_USER_WRONG_USERNAME)
                        .param("firstName", TEST_USER_FIRST_NAME)
                        .param("lastName", TEST_USER_LAST_NAME)
                        .param("email", "testTwo@abv.bg")
                        .param("age", String.valueOf(TEST_USER_AGE))
                        .param("bornOn", String.valueOf(TEST_USER_BORN_ON))
                        .param("country", TEST_USER_COUNTRY)
                        .param("password", TEST_USER_PASSWORD)
                        .param("confirmPassword", TEST_USER_CONFIRM_PASSWORD)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("register"))
                .andExpect(flash().attributeExists("userRegisterBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.userRegisterBindingModel"))
                .andExpect(flash().attributeCount(2));
    }

    @Test
    void testRegisterUserWithExistingUsername() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("username", "admin")
                        .param("firstName", TEST_USER_FIRST_NAME)
                        .param("lastName", TEST_USER_LAST_NAME)
                        .param("email", TEST_USER_EMAIL)
                        .param("age", String.valueOf(TEST_USER_AGE))
                        .param("bornOn", String.valueOf(TEST_USER_BORN_ON))
                        .param("country", TEST_USER_COUNTRY)
                        .param("password", TEST_USER_PASSWORD)
                        .param("confirmPassword", TEST_USER_CONFIRM_PASSWORD)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("register"))
                .andExpect(flash().attributeExists("userRegisterBindingModel"))
                .andExpect(flash().attributeExists("isUserOrEmailOccupied"))
                .andExpect(flash().attributeCount(2));
    }

    @Test
    void testRegisterUserWithExistingEmail() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("username", "ivanDragan")
                        .param("firstName", TEST_USER_FIRST_NAME)
                        .param("lastName", TEST_USER_LAST_NAME)
                        .param("email", "admin@abv.bg")
                        .param("age", String.valueOf(TEST_USER_AGE))
                        .param("bornOn", String.valueOf(TEST_USER_BORN_ON))
                        .param("country", TEST_USER_COUNTRY)
                        .param("password", TEST_USER_PASSWORD)
                        .param("confirmPassword", TEST_USER_CONFIRM_PASSWORD)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("register"))
                .andExpect(flash().attributeExists("userRegisterBindingModel"))
                .andExpect(flash().attributeExists("isUserOrEmailOccupied"))
                .andExpect(flash().attributeCount(2));
    }

    @Test
    void testOpenLoginForm() throws Exception {
        mockMvc
                .perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginUserWithWrongInfo() throws Exception {
        mockMvc.perform(post("/users/login-error")
                .param("username", "invalidUsername")
                .param("password", "invalidPassword")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @WithMockUser("admin")
    @Test
    void testLoginUserMyCoupons() throws Exception {
        mockMvc
                .perform(get("/users/my-coupons"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-coupons"))
                .andExpect(model().attributeExists("notActiveUserCoupons"))
                .andExpect(model().attributeExists("activeUserCoupons"));
    }

    @WithMockUser("admin")
    @Test
    void testLoginUserProfile() throws Exception {
        mockMvc
                .perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("info"));
    }

    @WithMockUser("admin")
    @Test
    void testLoginUserAddMoney() throws Exception {
        mockMvc
                .perform(get("/users/profile/add-money"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/users/profile"));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testLoginUserWarehouse() throws Exception {
        mockMvc
                .perform(get("/users/warehouse"))
                .andExpect(status().isOk())
                .andExpect(view().name("warehouse"));
    }

    @WithMockUser(username = "admin")
    @Test
    void testLoginUserActivateCoupon() throws Exception {
        mockMvc
                .perform(get("/users/my-coupons/" + couponEntity.getId() + "/activate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/users/my-coupons"));

    }

    @WithMockUser(username = "admin")
    @Test
    void testLoginUserActivateCouponWhichNotExists() throws Exception {
        mockMvc
                .perform(get("/users/my-coupons/" + 10 + "/activate"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("object-not-found"))
                .andExpect(model().attributeExists("message"));

    }



}
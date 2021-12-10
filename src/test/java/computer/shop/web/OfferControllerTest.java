package computer.shop.web;

import computer.shop.models.entity.*;
import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.entity.enums.componentsEnums.*;
import computer.shop.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComputerOfferRepository computerOfferRepository;

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private DriveRepository driveRepository;

    @Autowired
    private PowerSupplyRepository powerSupplyRepository;

    @Autowired
    private ProcessorRepository processorRepository;

    @Autowired
    private RandomAccessMemoryRepository randomAccessMemoryRepository;

    @Autowired
    private VideoCardRepository videoCardRepository;

    @Autowired
    private SmartphoneOfferRepository smartphoneOfferRepository;

    @Autowired
    private SmartphoneRepository smartphoneRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private ComputerEntity computerTest;
    private SmartphoneEntity smartphoneTest;

    private ComputerOfferEntity computerOfferEntityTest;
    private SmartphoneOfferEntity smartphoneOfferEntityTest;

    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;

    @BeforeEach
    void setUp() {
        computerTest = initComputer();
        smartphoneTest = initSmartphone();

        adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        userRoleRepository.save(adminRole);
        userRoleRepository.save(userRole);

        testUser = new UserEntity()
                .setUsername("admin")
                .setFirstName("Gosho")
                .setLastName("Georgiev")
                .setPassword("test")
                .setEmail("gosho@abv.bg")
                .setAge(56)
                .setBornOn(LocalDate.now())
                .setBalance(BigDecimal.valueOf(10000))
                .setCountry("Bulgaria")
                .setRoles(Set.of(adminRole, userRole));

        testUser = userRepository.save(testUser);

        computerOfferEntityTest = initComputerOffer();
        smartphoneOfferEntityTest = initSmartphoneOffer();
    }

    private SmartphoneEntity initSmartphone() {
        SmartphoneEntity smartphone = new SmartphoneEntity();

        smartphone
                .setBackCameraMP(15)
                .setBatteryCapacity(5000)
                .setBuiltInMemory(125)
                .setChipset("Chipset")
                .setCompanyName("Company name")
                .setDescription("Smartphone description.")
                .setDisplayInches(6.0)
                .setFrontCameraMP(15)
                .setManufacturedOn(LocalDate.now())
                .setModelName("Kosatka")
                .setOperationalSystem("Android")
                .setPrice(BigDecimal.valueOf(100))
                .setProcessor("Processor")
                .setPublished(false)
                .setRam(4);

        return smartphoneRepository.save(smartphone);
    }

    private ComputerEntity initComputer() {
        ComputerEntity computer = new ComputerEntity();

        VideoCardEntity videoCard = new VideoCardEntity()
                .setDescription("random text")
                .setGpuCores(500)
                .setMemory("Memory")
                .setName(VideoCardEnum.Gigabyte_Aorus_GeForce_RTX_3080)
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

        double price =
                videoCard.getPrice().doubleValue() +
                        processor.getPrice().doubleValue() +
                        randomAccessMemoryEntity.getPrice().doubleValue() +
                        powerSupply.getPrice().doubleValue() +
                        drive.getPrice().doubleValue();

        videoCardRepository.save(videoCard);
        processorRepository.save(processor);
        randomAccessMemoryRepository.save(randomAccessMemoryEntity);
        powerSupplyRepository.save(powerSupply);
        driveRepository.save(drive);

        computer
                .setName("Killer")
                .setDescription("Computer description.")
                .setManufacturedOn(LocalDate.now())
                .setComputerBox(ComputerBoxEnum.MID_TOWER)
                .setPublished(false)
                .setVideoCard(videoCard)
                .setProcessor(processor)
                .setRam(randomAccessMemoryEntity)
                .setPowerSupply(powerSupply)
                .setDrive(drive)
                .setPrice(BigDecimal.valueOf(price));

        return computerRepository.save(computer);
    }

    private ComputerOfferEntity initComputerOffer(){
        ComputerOfferEntity computerOfferEntity = new ComputerOfferEntity()
                .setComputer(computerTest)
                .setPrice(computerTest.getPrice())
                .setName("compOfferTest")
                .setSeller(testUser)
                .setCreatedOn(LocalDate.now())
                .setDescription("Computer offer test description.");

        return computerOfferRepository.save(computerOfferEntity);
    }

    private SmartphoneOfferEntity initSmartphoneOffer(){
        SmartphoneOfferEntity smartphoneOfferEntity = new SmartphoneOfferEntity()
                .setSmartphone(smartphoneTest)
                .setSeller(testUser)
                .setName("smartOfferTest")
                .setCreatedOn(LocalDate.now())
                .setPrice(smartphoneTest.getPrice())
                .setDescription("Smartphone offer test description.");

        return smartphoneOfferRepository.save(smartphoneOfferEntity);
    }

    @AfterEach
    void tearDown() {
        computerOfferRepository.deleteAll();
        computerRepository.deleteAll();

        smartphoneOfferRepository.deleteAll();
        smartphoneRepository.deleteAll();

        couponRepository.deleteAll();

        userRepository.deleteAll();
        userRoleRepository.deleteAll();

        videoCardRepository.deleteAll();
        processorRepository.deleteAll();
        randomAccessMemoryRepository.deleteAll();
        powerSupplyRepository.deleteAll();
        driveRepository.deleteAll();
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testAddComputerOfferView() throws Exception {
        mockMvc.perform(get("/offers/add-computer-offer"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-computer-offer"))
                .andExpect(model().attributeExists("computers"));
    }

    private static final String TEST_COMPUTER_OFFER_NAME = "Computer offer";
    private static final LocalDate TEST_COMPUTER_OFFER_CREATED_ON = LocalDate.now();
    private static final String TEST_COMPUTER_OFFER_COMPUTER = "Killer";
    private static final String TEST_COMPUTER_OFFER_DESCRIPTION = "Computer offer description";

    private static final String TEST_COMPUTER_OFFER_WRONG_OFFER_NAME = "X";


    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testAddComputerOffer() throws Exception {
        mockMvc.perform(post("/offers/add-computer-offer")
                        .param("name", TEST_COMPUTER_OFFER_NAME)
                        .param("createdOn", String.valueOf(TEST_COMPUTER_OFFER_CREATED_ON))
                        .param("computer", TEST_COMPUTER_OFFER_COMPUTER)
                        .param("description", TEST_COMPUTER_OFFER_DESCRIPTION)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/computers/catalog"));

        Assertions.assertEquals(2, computerOfferRepository.count());

        Optional<ComputerOfferEntity> computerOfferOpt = computerOfferRepository.findByName(TEST_COMPUTER_OFFER_NAME);

        Assertions.assertTrue(computerOfferOpt.isPresent());

        ComputerOfferEntity computerOffer = computerOfferOpt.get();

        Assertions.assertEquals(TEST_COMPUTER_OFFER_NAME, computerOffer.getName());
        Assertions.assertEquals(TEST_COMPUTER_OFFER_CREATED_ON, computerOffer.getCreatedOn());
        Assertions.assertEquals(TEST_COMPUTER_OFFER_COMPUTER, computerOffer.getComputer().getName());
        Assertions.assertEquals(TEST_COMPUTER_OFFER_DESCRIPTION, computerOffer.getDescription());
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testAddComputerOfferWithWrongInfo() throws Exception {
        mockMvc.perform(post("/offers/add-computer-offer")
                        .param("name", TEST_COMPUTER_OFFER_WRONG_OFFER_NAME)
                        .param("createdOn", String.valueOf(TEST_COMPUTER_OFFER_CREATED_ON))
                        .param("computer", TEST_COMPUTER_OFFER_COMPUTER)
                        .param("description", TEST_COMPUTER_OFFER_DESCRIPTION)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/add-computer-offer"))
                .andExpect(flash().attributeExists("computerOfferAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.computerOfferAddBindingModel"))
                .andExpect(flash().attributeCount(2));
        ;
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testAddSmartphoneOfferView() throws Exception {
        mockMvc.perform(get("/offers/add-smartphone-offer"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-smartphone-offer"))
                .andExpect(model().attributeExists("smartphones"));
    }

    private static final String TEST_SMARTPHONE_OFFER_NAME = "Smartphone offer";
    private static final LocalDate TEST_SMARTPHONE_OFFER_CREATED_ON = LocalDate.now();
    private static final String TEST_SMARTPHONE_OFFER_DESCRIPTION = "Smartphone offer description";
    private static final String TEST_SMARTPHONE_OFFER_SMARTPHONE = "Kosatka";

    private static final String TEST_SMARTPHONE_OFFER_WRONG_NAME = "X";


    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testAddSmartphoneOffer() throws Exception {
        mockMvc.perform(post("/offers/add-smartphone-offer")
                        .param("name", TEST_SMARTPHONE_OFFER_NAME)
                        .param("createdOn", String.valueOf(TEST_SMARTPHONE_OFFER_CREATED_ON))
                        .param("description", TEST_SMARTPHONE_OFFER_DESCRIPTION)
                        .param("smartphone", TEST_SMARTPHONE_OFFER_SMARTPHONE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/smartphones/catalog"));

        Assertions.assertEquals(2, smartphoneOfferRepository.count());

        Optional<SmartphoneOfferEntity> smartphoneOfferOpt = smartphoneOfferRepository.findByName(TEST_SMARTPHONE_OFFER_NAME);

        Assertions.assertTrue(smartphoneOfferOpt.isPresent());

        SmartphoneOfferEntity smartphoneOffer = smartphoneOfferOpt.get();

        Assertions.assertEquals(TEST_SMARTPHONE_OFFER_NAME, smartphoneOffer.getName());
        Assertions.assertEquals(TEST_SMARTPHONE_OFFER_CREATED_ON, smartphoneOffer.getCreatedOn());
        Assertions.assertEquals(TEST_SMARTPHONE_OFFER_DESCRIPTION, smartphoneOffer.getDescription());
        Assertions.assertEquals(TEST_SMARTPHONE_OFFER_SMARTPHONE, smartphoneOffer.getSmartphone().getModelName());
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testAddSmartphoneOfferWithWrongInfo() throws Exception {
        mockMvc.perform(post("/offers/add-smartphone-offer")
                        .param("name", TEST_SMARTPHONE_OFFER_WRONG_NAME)
                        .param("createdOn", String.valueOf(TEST_SMARTPHONE_OFFER_CREATED_ON))
                        .param("description", TEST_SMARTPHONE_OFFER_DESCRIPTION)
                        .param("smartphone", TEST_SMARTPHONE_OFFER_SMARTPHONE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/add-smartphone-offer"))
                .andExpect(flash().attributeExists("smartphoneOfferAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.smartphoneOfferAddBindingModel"))
                .andExpect(flash().attributeCount(2));
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerDetailsView() throws Exception {
        mockMvc.perform(get("/offers/computers/" + computerOfferEntityTest.getId() + "/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("computer"))
                .andExpect(view().name("computer-details"));
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerDetailsViewIsNotExists() throws Exception {
        mockMvc.perform(get("/offers/computers/" + 10000 + "/details"))
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("object-not-found"));
    }

    //TODO DELETE
//    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
//    @Test
//    void testComputerOfferDeleteSuccess() throws Exception {
//        mockMvc.perform(delete("/offers/computers/" + computerOfferEntityTest.getId() + "/details")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/computers/catalog"));
//    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferEditView() throws Exception {
        mockMvc.perform(get("/offers/computers/" + computerOfferEntityTest.getId() + "/edit")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("computer-update"))
                .andExpect(model().attributeExists("computers"))
                .andExpect(model().attributeExists("currentComputerName"))
                .andExpect(model().attributeExists("computerUpdateBindingModel"));
    }

    private static final String TEST_COMPUTER_OFFER_EDIT_NAME = "EditedName";
    private static final LocalDate TEST_COMPUTER_OFFER_EDIT_CREATED_ON = LocalDate.now();
    private static final String TEST_COMPUTER_OFFER_EDIT_COMPUTER_NAME = TEST_COMPUTER_OFFER_COMPUTER;
    private static final String TEST_COMPUTER_OFFER_EDIT_DESCRIPTION = "EditedDescription";

    private static final String TEST_COMPUTER_OFFER_EDIT_WRONG_NAME = "X";

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferEdit() throws Exception {
        mockMvc.perform(patch("/offers/computers/" + computerOfferEntityTest.getId() + "/edit")
                        .param("name", TEST_COMPUTER_OFFER_EDIT_NAME)
                        .param("createdOn", String.valueOf(TEST_COMPUTER_OFFER_EDIT_CREATED_ON))
                        .param("computerName", TEST_COMPUTER_OFFER_EDIT_COMPUTER_NAME)
                        .param("description", TEST_COMPUTER_OFFER_EDIT_DESCRIPTION)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/computers/" + computerOfferEntityTest.getId() + "/details"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferEditWithWrongInfo() throws Exception {
        mockMvc.perform(patch("/offers/computers/" + computerOfferEntityTest.getId() + "/edit")
                        .param("name", TEST_COMPUTER_OFFER_EDIT_WRONG_NAME)
                        .param("createdOn", String.valueOf(TEST_COMPUTER_OFFER_EDIT_CREATED_ON))
                        .param("computerName", TEST_COMPUTER_OFFER_EDIT_COMPUTER_NAME)
                        .param("description", TEST_COMPUTER_OFFER_EDIT_DESCRIPTION)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/computers/" + computerOfferEntityTest.getId() + "/edit/errors"))
                .andExpect(view().name("redirect:/offers/computers/" + computerOfferEntityTest.getId() + "/edit/errors"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferBuyView() throws Exception {
        mockMvc.perform(get("/offers/computers/" + computerOfferEntityTest.getId() + "/buy"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("couponsNames"))
                .andExpect(model().attributeExists("offerDetails"))
                .andExpect(model().attributeExists("canUserBuy"))
                .andExpect(view().name("computer-buy"));
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferBuy() throws Exception {
        mockMvc.perform(post("/offers/computers/" + computerOfferEntityTest.getId() + "/buy")
                .param("couponName", "15% COUPON")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferBuyWithoutCoupon() throws Exception {
        mockMvc.perform(post("/offers/computers/" + computerOfferEntityTest.getId() + "/buy")
                .param("couponName", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testComputerOfferBuyWithNoHavingEnoughMoney() throws Exception {

        testUser.setBalance(BigDecimal.valueOf(0));
        userRepository.save(testUser);

        mockMvc.perform(post("/offers/computers/" + computerOfferEntityTest.getId() + "/buy")
                .param("couponName", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("canUserBuy"))
                .andExpect(flash().attributeExists("couponBuyBindingModel"))
                .andExpect(flash().attributeCount(2))
                .andExpect(redirectedUrl("/offers/computers/" + computerOfferEntityTest.getId() + "/buy"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneDetailsView() throws Exception {
        mockMvc.perform(get("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("smartphone"))
                .andExpect(view().name("smartphone-details"));
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneDetailsViewIsNotExists() throws Exception {
        mockMvc.perform(get("/offers/smartphones/" + 10000 + "/details"))
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("object-not-found"));
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferEditView() throws Exception {
        mockMvc.perform(get("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/edit")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("smartphone-update"))
                .andExpect(model().attributeExists("smartphones"))
                .andExpect(model().attributeExists("currentSmartphoneName"))
                .andExpect(model().attributeExists("smartphoneUpdateBindingModel"));
    }

    private static final String TEST_SMARTPHONE_OFFER_EDIT_NAME = "EditedName";
    private static final LocalDate TEST_SMARTPHONE_OFFER_EDIT_CREATED_ON = LocalDate.now();
    private static final String TEST_SMARTPHONE_OFFER_EDIT_SMARTPHONE_NAME = TEST_SMARTPHONE_OFFER_SMARTPHONE;
    private static final String TEST_SMARTPHONE_OFFER_EDIT_DESCRIPTION = "EditedDescription";

    private static final String TEST_SMARTPHONE_OFFER_EDIT_WRONG_NAME = "X";

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferEdit() throws Exception {
        mockMvc.perform(patch("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/edit")
                        .param("name", TEST_SMARTPHONE_OFFER_EDIT_NAME)
                        .param("createdOn", String.valueOf(TEST_SMARTPHONE_OFFER_EDIT_CREATED_ON))
                        .param("smartphoneModelName", TEST_SMARTPHONE_OFFER_EDIT_SMARTPHONE_NAME)
                        .param("description", TEST_SMARTPHONE_OFFER_EDIT_DESCRIPTION)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/details"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferEditWithWrongInfo() throws Exception {
        mockMvc.perform(patch("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/edit")
                        .param("name", TEST_SMARTPHONE_OFFER_EDIT_WRONG_NAME)
                        .param("createdOn", String.valueOf(TEST_SMARTPHONE_OFFER_EDIT_CREATED_ON))
                        .param("smartphoneModelName", TEST_SMARTPHONE_OFFER_EDIT_SMARTPHONE_NAME)
                        .param("description", TEST_SMARTPHONE_OFFER_EDIT_DESCRIPTION)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/edit/errors"))
                .andExpect(view().name("redirect:/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/edit/errors"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferBuyView() throws Exception {
        mockMvc.perform(get("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/buy"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("couponsNames"))
                .andExpect(model().attributeExists("offerDetails"))
                .andExpect(model().attributeExists("canUserBuy"))
                .andExpect(view().name("smartphone-buy"));
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferBuyWithCoupon() throws Exception {
        mockMvc.perform(post("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/buy")
                        .param("couponName", "15% COUPON")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferBuyWithoutCoupon() throws Exception {
        mockMvc.perform(post("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/buy")
                        .param("couponName", "")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testSmartphoneOfferBuyWithNoHavingEnoughMoney() throws Exception {

        testUser.setBalance(BigDecimal.valueOf(0));
        userRepository.save(testUser);

        mockMvc.perform(post("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/buy")
                        .param("couponName", "")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("canUserBuy"))
                .andExpect(flash().attributeExists("couponBuyBindingModel"))
                .andExpect(flash().attributeCount(2))
                .andExpect(redirectedUrl("/offers/smartphones/" + smartphoneOfferEntityTest.getId() + "/buy"));

    }
}
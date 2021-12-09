package computer.shop.web;

import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.repository.SmartphoneOfferRepository;
import computer.shop.repository.SmartphoneRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class SmartphoneControllerTest {

    private static final String TEST_SMARTPHONE_MODEL_NAME = "Test Model Name";
    private static final String TEST_SMARTPHONE_COMPANY_NAME = "Test Company name";
    private static final String TEST_SMARTPHONE_OPERATIONAL_SYSTEM = "Test Op System";
    private static final String TEST_SMARTPHONE_CHIPSET = "Test Chipset";
    private static final String TEST_SMARTPHONE_PROCESSOR = "Test Processor";
    private static final Integer TEST_SMARTPHONE_RAM = 4;
    private static final Integer TEST_SMARTPHONE_BUILT_IN_MEMORY = 128000;
    private static final Integer TEST_SMARTPHONE_FRONT_CAMERA_MP = 25;
    private static final Integer TEST_SMARTPHONE_BACK_CAMERA_MP = 108;
    private static final Integer TEST_SMARTPHONE_BATTERY_CAPACITY = 5000;
    private static final Double TEST_SMARTPHONE_DISPLAY_INCHES = 6.5;
    private static final double TEST_SMARTPHONE_PRICE = 1000;
    private static final LocalDate TEST_SMARTPHONE_MANUFACTURED_ON = LocalDate.now();
    private static final String TEST_SMARTPHONE_DESCRIPTION = "Description about phone";

    private static final String TEST_SMARTPHONE_WRONG_MODEL_NAME = "X";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SmartphoneRepository smartphoneRepository;

    @Autowired
    private SmartphoneOfferRepository smartphoneOfferRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        smartphoneRepository.deleteAll();
    }

    @WithMockUser("admin")
    @Test
    void testSmartphonesCatalog() throws Exception {
        mockMvc.perform(get("/smartphones/catalog"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("smartphonesCatalog"))
                .andExpect(view().name("smartphones-catalog"));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testAddSmartphoneForm() throws Exception {
        mockMvc.perform(get("/smartphones/add-smartphone"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-smartphone"));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testAddSmartphone() throws Exception {
        mockMvc.perform(post("/smartphones/add-smartphone")
                .param("companyName", TEST_SMARTPHONE_COMPANY_NAME)
                .param("modelName", TEST_SMARTPHONE_MODEL_NAME)
                .param("operationalSystem", TEST_SMARTPHONE_OPERATIONAL_SYSTEM)
                .param("chipset", TEST_SMARTPHONE_CHIPSET)
                .param("processor", TEST_SMARTPHONE_PROCESSOR)
                .param("ram", String.valueOf(TEST_SMARTPHONE_RAM))
                .param("builtInMemory", String.valueOf(TEST_SMARTPHONE_BUILT_IN_MEMORY))
                .param("frontCameraMP", String.valueOf(TEST_SMARTPHONE_FRONT_CAMERA_MP))
                .param("backCameraMP", String.valueOf(TEST_SMARTPHONE_BACK_CAMERA_MP))
                .param("batteryCapacity", String.valueOf(TEST_SMARTPHONE_BATTERY_CAPACITY))
                .param("displayInches", String.valueOf(TEST_SMARTPHONE_DISPLAY_INCHES))
                .param("price", String.valueOf(TEST_SMARTPHONE_PRICE))
                .param("manufacturedOn", String.valueOf(TEST_SMARTPHONE_MANUFACTURED_ON))
                .param("description", TEST_SMARTPHONE_DESCRIPTION)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/smartphones/catalog"));

        Assertions.assertEquals(1, smartphoneRepository.count());

        Optional<SmartphoneEntity> smartphoneOpt = smartphoneRepository.findByModelName(TEST_SMARTPHONE_MODEL_NAME);

        Assertions.assertTrue(smartphoneOpt.isPresent());

        SmartphoneEntity smartphone = smartphoneOpt.get();

        Assertions.assertEquals(TEST_SMARTPHONE_COMPANY_NAME, smartphone.getCompanyName());
        Assertions.assertEquals(TEST_SMARTPHONE_MODEL_NAME, smartphone.getModelName());
        Assertions.assertEquals(TEST_SMARTPHONE_OPERATIONAL_SYSTEM, smartphone.getOperationalSystem());
        Assertions.assertEquals(TEST_SMARTPHONE_CHIPSET, smartphone.getChipset());
        Assertions.assertEquals(TEST_SMARTPHONE_PROCESSOR, smartphone.getProcessor());
        Assertions.assertEquals(TEST_SMARTPHONE_RAM, smartphone.getRam());
        Assertions.assertEquals(TEST_SMARTPHONE_BUILT_IN_MEMORY, smartphone.getBuiltInMemory());
        Assertions.assertEquals(TEST_SMARTPHONE_FRONT_CAMERA_MP, smartphone.getFrontCameraMP());
        Assertions.assertEquals(TEST_SMARTPHONE_BACK_CAMERA_MP, smartphone.getBackCameraMP());
        Assertions.assertEquals(TEST_SMARTPHONE_BATTERY_CAPACITY, smartphone.getBatteryCapacity());
        Assertions.assertEquals(TEST_SMARTPHONE_DISPLAY_INCHES, smartphone.getDisplayInches());
        Assertions.assertEquals(TEST_SMARTPHONE_PRICE, smartphone.getPrice().doubleValue());
        Assertions.assertEquals(TEST_SMARTPHONE_MANUFACTURED_ON, smartphone.getManufacturedOn());
        Assertions.assertEquals(TEST_SMARTPHONE_DESCRIPTION, smartphone.getDescription());
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testAddSmartphoneWithWrongInfo() throws Exception {
        mockMvc.perform(post("/smartphones/add-smartphone")
                .param("companyName", TEST_SMARTPHONE_COMPANY_NAME)
                .param("modelName", TEST_SMARTPHONE_WRONG_MODEL_NAME)
                .param("operationalSystem", TEST_SMARTPHONE_OPERATIONAL_SYSTEM)
                .param("chipset", TEST_SMARTPHONE_CHIPSET)
                .param("processor", TEST_SMARTPHONE_PROCESSOR)
                .param("ram", String.valueOf(TEST_SMARTPHONE_RAM))
                .param("builtInMemory", String.valueOf(TEST_SMARTPHONE_BUILT_IN_MEMORY))
                .param("frontCameraMP", String.valueOf(TEST_SMARTPHONE_FRONT_CAMERA_MP))
                .param("backCameraMP", String.valueOf(TEST_SMARTPHONE_BACK_CAMERA_MP))
                .param("batteryCapacity", String.valueOf(TEST_SMARTPHONE_BATTERY_CAPACITY))
                .param("displayInches", String.valueOf(TEST_SMARTPHONE_DISPLAY_INCHES))
                .param("price", String.valueOf(TEST_SMARTPHONE_PRICE))
                .param("manufacturedOn", String.valueOf(TEST_SMARTPHONE_MANUFACTURED_ON))
                .param("description", TEST_SMARTPHONE_DESCRIPTION)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("smartphoneAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.smartphoneAddBindingModel"))
                .andExpect(flash().attributeCount(2))
                .andExpect(redirectedUrl("/smartphones/add-smartphone"));
    }
}
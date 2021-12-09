package computer.shop.web;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ComputerControllerTest {

    private static final String TEST_COMPUTER_NAME = "Killer-Machine";
    private static final String TEST_COMPUTER_DESCRIPTION = "Description";
    private static final LocalDate TEST_COMPUTER_MANUFACTURED_ON = LocalDate.now();
    private static final String TEST_COMPUTER_COMPUTER_BOX = ComputerBoxEnum.MID_TOWER.name();
    private static final String TEST_COMPUTER_VIDEO_CARD = VideoCardEnum.AMD_Radeon_RX_5700.name();
    private static final String TEST_COMPUTER_PROCESSOR = ProcessorEnum.AMD_Ryzen_7_2700X.name();
    private static final String TEST_COMPUTER_RAM = RandomAccessMemoryEnum.Colorful_CVN_Guardian_16GB_DDR4_3200.name();
    private static final String TEST_COMPUTER_POWER_SUPPLY = PowerSupplyEnum.Corsair_AX1600i.name();
    private static final String TEST_COMPUTER_DRIVE = DriveEnum.Crucial_P5_Plus.name();

    private static final String TEST_COMPUTER_WRONG_NAME = "X";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private ComputerOfferRepository computerOfferRepository;

    @Autowired
    private VideoCardRepository videoCardRepository;

    @Autowired
    private ProcessorRepository processorRepository;

    @Autowired
    private RandomAccessMemoryRepository randomAccessMemoryRepository;

    @Autowired
    private PowerSupplyRepository powerSupplyRepository;

    @Autowired
    private DriveRepository driveRepository;

    @BeforeEach
    void setUp(){
        initComponents();
    }

    @AfterEach
    void tearDown() {
        computerRepository.deleteAll();
        videoCardRepository.deleteAll();
        processorRepository.deleteAll();
        randomAccessMemoryRepository.deleteAll();
        powerSupplyRepository.deleteAll();
        driveRepository.deleteAll();
    }

    private void initComponents(){
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

        videoCardRepository.save(videoCard);
        processorRepository.save(processor);
        randomAccessMemoryRepository.save(randomAccessMemoryEntity);
        powerSupplyRepository.save(powerSupply);
        driveRepository.save(drive);
    }

    @WithMockUser("admin")
    @Test
    void testComputersCatalog() throws Exception {
        mockMvc.perform(get("/computers/catalog"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("computersCatalog"))
                .andExpect(view().name("computers-catalog"));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testAddComputerForm() throws Exception {
        mockMvc.perform(get("/computers/add-computer"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-computer"));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testAddComputer() throws Exception {
        mockMvc.perform(post("/computers/add-computer")
                .param("name", TEST_COMPUTER_NAME)
                .param("description", TEST_COMPUTER_DESCRIPTION)
                .param("manufacturedOn", String.valueOf(TEST_COMPUTER_MANUFACTURED_ON))
                .param("computerBox", TEST_COMPUTER_COMPUTER_BOX)
                .param("videoCard", TEST_COMPUTER_VIDEO_CARD)
                .param("processor", TEST_COMPUTER_PROCESSOR)
                .param("ram", TEST_COMPUTER_RAM)
                .param("powerSupply", TEST_COMPUTER_POWER_SUPPLY)
                .param("drive", TEST_COMPUTER_DRIVE)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/computers/catalog"));

        Assertions.assertEquals(1, computerRepository.count());

        Optional<ComputerEntity> computerOpt = computerRepository.findByName(TEST_COMPUTER_NAME);

        Assertions.assertTrue(computerOpt.isPresent());

        ComputerEntity computer = computerOpt.get();

        Assertions.assertEquals(TEST_COMPUTER_NAME, computer.getName());
        Assertions.assertEquals(TEST_COMPUTER_DESCRIPTION, computer.getDescription());
        Assertions.assertEquals(TEST_COMPUTER_MANUFACTURED_ON, computer.getManufacturedOn());
        Assertions.assertEquals(TEST_COMPUTER_COMPUTER_BOX, computer.getComputerBox().name());
        Assertions.assertEquals(TEST_COMPUTER_VIDEO_CARD, computer.getVideoCard().getName().name());
        Assertions.assertEquals(TEST_COMPUTER_PROCESSOR, computer.getProcessor().getName().name());
        Assertions.assertEquals(TEST_COMPUTER_RAM, computer.getRam().getName().name());
        Assertions.assertEquals(TEST_COMPUTER_POWER_SUPPLY, computer.getPowerSupply().getName().name());
        Assertions.assertEquals(TEST_COMPUTER_DRIVE, computer.getDrive().getName().name());

    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testAddComputerWithWrongInfo() throws Exception {
        mockMvc.perform(post("/computers/add-computer")
                .param("name", TEST_COMPUTER_WRONG_NAME)
                .param("description", TEST_COMPUTER_DESCRIPTION)
                .param("manufacturedOn", String.valueOf(TEST_COMPUTER_MANUFACTURED_ON))
                .param("computerBox", TEST_COMPUTER_COMPUTER_BOX)
                .param("videoCard", TEST_COMPUTER_VIDEO_CARD)
                .param("processor", TEST_COMPUTER_PROCESSOR)
                .param("ram", TEST_COMPUTER_RAM)
                .param("powerSupply", TEST_COMPUTER_POWER_SUPPLY)
                .param("drive", TEST_COMPUTER_DRIVE)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/computers/add-computer"))
                .andExpect(flash().attributeExists("computerAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.computerAddBindingModel"))
                .andExpect(flash().attributeCount(2));
    }

}
package computer.shop.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.*;
import computer.shop.models.entity.enums.ComputerBoxEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.entity.enums.componentsEnums.*;
import computer.shop.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@WithMockUser(username="admin",roles={"USER","ADMIN"})
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseRestControllerTest {

    private final static String COMPUTER_DESCRIPTION = "Killer is unbeatable machine.";
    private final static String SMARTPHONE_DESCRIPTION_1 = "Description about smartphone one.";
    private final static String SMARTPHONE_DESCRIPTION_2 = "Description about smartphone two.";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SmartphoneRepository smartphoneRepository;

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
    private ComputerRepository computerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;

    @BeforeEach
    void setUp() {

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
                .setBalance(BigDecimal.valueOf(1000))
                .setCountry("Bulgaria")
                .setRoles(Set.of(adminRole,userRole));

        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        computerRepository.deleteAll();

        driveRepository.deleteAll();
        videoCardRepository.deleteAll();
        powerSupplyRepository.deleteAll();
        randomAccessMemoryRepository.deleteAll();
        processorRepository.deleteAll();

        userRoleRepository.deleteAll();
        smartphoneRepository.deleteAll();
    }

    @Test
    void testGetComputers() throws Exception {
        initComputers();

        mockMvc.perform(get("/api/warehouse/computers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].description", is(COMPUTER_DESCRIPTION)));
    }

    @Test
    void testGetSmartphones() throws Exception {
        initSmartphones();

        mockMvc.perform(get("/api/warehouse/smartphones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].description", is(SMARTPHONE_DESCRIPTION_1)))
                .andExpect(jsonPath("$.[1].description", is(SMARTPHONE_DESCRIPTION_2)));
    }

    private ComputerEntity initComputers() {
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
                .setDescription(COMPUTER_DESCRIPTION)
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

    private void initSmartphones(){
        SmartphoneEntity smartphoneOne = new SmartphoneEntity();
        SmartphoneEntity smartphoneTwo = new SmartphoneEntity();

        smartphoneOne
                .setBackCameraMP(15)
                .setBatteryCapacity(5000)
                .setBuiltInMemory(125)
                .setChipset("Chipset")
                .setCompanyName("Company name")
                .setDescription(SMARTPHONE_DESCRIPTION_1)
                .setDisplayInches(6.0)
                .setFrontCameraMP(15)
                .setManufacturedOn(LocalDate.now())
                .setModelName("Model name one")
                .setOperationalSystem("Android")
                .setPrice(BigDecimal.valueOf(100))
                .setProcessor("Processor")
                .setPublished(false)
                .setRam(4);

        smartphoneTwo
                .setBackCameraMP(20)
                .setBatteryCapacity(5000)
                .setBuiltInMemory(125)
                .setChipset("Chipset")
                .setCompanyName("Company name")
                .setDescription(SMARTPHONE_DESCRIPTION_2)
                .setDisplayInches(5.0)
                .setFrontCameraMP(20)
                .setManufacturedOn(LocalDate.now())
                .setModelName("Model name two")
                .setOperationalSystem("Android")
                .setPrice(BigDecimal.valueOf(100))
                .setProcessor("Processor")
                .setPublished(false)
                .setRam(5);

       smartphoneRepository.save(smartphoneOne);
       smartphoneRepository.save(smartphoneTwo);
    }

}

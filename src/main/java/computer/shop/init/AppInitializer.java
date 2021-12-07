package computer.shop.init;

import computer.shop.repository.*;
import computer.shop.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class AppInitializer implements CommandLineRunner {

    private final DriveService driveService;
    private final PowerSupplyService powerSupplyService;
    private final ProcessorService processorService;
    private final RandomAccessMemoryService randomAccessMemoryService;
    private final VideoCardService videoCardService;
    private final CouponService couponService;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    private final DriveRepository driveRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final ProcessorRepository processorRepository;
    private final RandomAccessMemoryRepository randomAccessMemoryRepository;
    private final VideoCardRepository videoCardRepository;
    private final CouponRepository couponRepository;
    private final UserRoleService userRoleService;
    private final UserService userService;

    public AppInitializer(DriveService driveService, PowerSupplyService powerSupplyService, ProcessorService processorService, RandomAccessMemoryService randomAccessMemoryService, VideoCardService videoCardService, CouponService couponService, UserRoleRepository userRoleRepository, UserRepository userRepository, DriveRepository driveRepository, PowerSupplyRepository powerSupplyRepository, ProcessorRepository processorRepository, RandomAccessMemoryRepository randomAccessMemoryRepository, VideoCardRepository videoCardRepository, CouponRepository couponRepository, UserRoleService userRoleService, UserService userService) {
        this.driveService = driveService;
        this.powerSupplyService = powerSupplyService;
        this.processorService = processorService;
        this.randomAccessMemoryService = randomAccessMemoryService;
        this.videoCardService = videoCardService;
        this.couponService = couponService;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.driveRepository = driveRepository;
        this.powerSupplyRepository = powerSupplyRepository;
        this.processorRepository = processorRepository;
        this.randomAccessMemoryRepository = randomAccessMemoryRepository;
        this.videoCardRepository = videoCardRepository;
        this.couponRepository = couponRepository;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if(driveRepository.count() == 0){
            driveService.initializeDrives();
        }
        if(powerSupplyRepository.count() == 0){
            powerSupplyService.initializePowerSupplies();
        }
        if(processorRepository.count() == 0){
            processorService.initializeProcessors();
        }
        if(randomAccessMemoryRepository.count() == 0){
            randomAccessMemoryService.initializeRAM();
        }
        if(videoCardRepository.count() == 0){
            videoCardService.initializeVideoCards();
        }
        if(couponRepository.count() == 0){
            couponService.initializeCoupons();
        }
        if(userRoleRepository.count() == 0){
            userRoleService.initializeRoles();
        }
        if(userRepository.count() == 0){
            userService.initializeUsers();
        }
    }
}

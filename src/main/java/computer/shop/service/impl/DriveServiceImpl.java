package computer.shop.service.impl;

import computer.shop.models.entity.enumEntity.DriveEntity;
import computer.shop.models.entity.enums.componentsEnums.DriveEnum;
import computer.shop.models.entity.enums.componentsEnums.DriveTypeEnum;
import computer.shop.repository.DriveRepository;
import computer.shop.service.DriveService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class DriveServiceImpl implements DriveService {

    private final DriveRepository driveRepository;

    public DriveServiceImpl(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    @Override
    public void initializeDrives() {
        Arrays.stream(DriveEnum.values())
                .forEach(driveEnum -> {
                    DriveEntity drive = null;
                    switch (driveEnum){
                        case Toshiba_X300:
                            drive = new DriveEntity(driveEnum, "4TB", 10, "Even if its laptops aren’t as popular as they used to be, Toshiba is still a huge name in computing, and has a lot to offer. When it comes to the best hard drives, the Toshiba X300 is a high-capacity, high-performance champ worth taking a look at. The X300 drives boast great gigabyte-to-dollar value without sacrificing on performance. These drives all spin at 7,200 rpm and include 128MB of cache for higher speeds. The only downside is the warranty only lasts two years, which feels short for a drive meant to store so much important data.", DriveTypeEnum.HARD_DISK_DRIVE, BigDecimal.valueOf(104.99));
                            break;
                        case WD_Black_SN850:
                            drive = new DriveEntity(driveEnum, "500GB", 5, "With ever-so-much faster random performance, a more consistent write profile, and higher efficiency, Samsung’s 980 PRO earned the title as our top pick for a next-gen PCIe 4.0 x4 NVMe, but WD’s Black SN850 makes for a top-tier runner-up. Depending on the price, you can’t go wrong with either one for your high-end gaming or workstation build. WD’s Black SN850 paired with the company’s new 16nm WD Black G2 PCIe 4.0 x4 NVMe 1.4 SSD controller marks a substantial improvement in the company’s SSD architecture. WDs Black SN850 can sustain speeds of up to 7/5.3 GBps and deliver very responsive random performance enabling the SSD to go toe-to-toe with our top pick. Although, that is at the cost of high idle power consumption on our desktop test bench. Also, unlike the Samsung 980 Pro, the WD Black SN850 lacks AES 256-bit encryption.", DriveTypeEnum.SOLID_STATE_DRIVE, BigDecimal.valueOf(99.99));
                            break;
                        case Crucial_P5_Plus:
                            drive = new DriveEntity(driveEnum, "1TB", 5, "Crucial’s P5 Plus is an evolution of the P5 with a focus on improved performance, especially where the original P5 let us down. Built for gamers and creative professionals who want faster load times and more efficient workflows, Crucial’s P5 Plus is a solid PCIe 4.0 x4 SSD that is priced well for its feature set. Although it banks on value more than flat-out performance, the P5 Plus proved capable of keeping up with the best in most applications. It features a host of specialized algorithms for data protection and hardware-based, OPAL 2.0-compliant AES 256-bit encryption for data security. If you can’t quite afford the Samsung 980 Pro or WD_Black SN850, the P5 Plus is a solid performing alternative that’s worthy of your consideration. ", DriveTypeEnum.SOLID_STATE_DRIVE, BigDecimal.valueOf(149));
                            break;
                        case Samsung_980_Pro:
                            drive = new DriveEntity(driveEnum, "1TB", 5, "For those looking for the best, look no further than the Samsung 980 PRO. Samsung pairs its in-house Elpis 8nm PCIe 4.0 x4 NVMe SSD controller with the company’s fastest V-NAND to unleash incredible performance. The Samsung 980 Pro serves up to 7/5 GBps of throughput and sustains upwards of a 1 million random read/write IOPS, making it the most responsive SSD we’ve tested. The drive comes with all the features you could want from a high-end NVMe SSD, making it the perfect drive for anyone who wants the best.", DriveTypeEnum.SOLID_STATE_DRIVE, BigDecimal.valueOf(189.99));
                            break;
                        case WD_Blue_Desktop:
                            drive = new DriveEntity(driveEnum, "6TB", 10, "Western Digital offers a solid bargain with its line of WD Blue hard drives. With a wide variety of storage options from a small 500GB to a capacious 6TB, the WD Blue is a viable pick for almost any type of PC build that’s sticking to a budget. The best value comes from the larger drives – they’ll give you much more storage per dollar spent. And, if you’re looking for a bit more speed, there are also 7,200rpm models available that don’t come with too much of a price hike.", DriveTypeEnum.HARD_DISK_DRIVE, BigDecimal.valueOf(39.99));
                            break;
                        case WD_VelociRaptor:
                            drive = new DriveEntity(driveEnum, "250GB", 6, "When it comes to PC gaming, it’s better to be fast than capacious. So, if you’ve been resisting the allure of an SSD, and looking to kick it old school with one of the best hard drives, the WD VelociRaptor should be up your alley. Not only does this drive have a whopping 10,000 rpm spin speed, but you’re going to want to pay attention to it. With capacities up to 1TB, the VelociRaptor drives are ready to store large game libraries, and the super fast platters will help your games launch and load quickly.", DriveTypeEnum.HARD_DISK_DRIVE, BigDecimal.valueOf(165));
                            break;
                        case Seagate_BarraCuda:
                            drive = new DriveEntity(driveEnum, "3TB", 10, "It’s almost impossible to talk about hard drives without mentioning Seagate’s BarraCuda lineup – it’s a force to be reckoned with. And, it’s not hard to see why, Seagate BarraCuda drives offer a great gigabyte-per-dollar ratio and speed benefits to top it off. The 2TB model hits a sweet spot by balancing high performance and affordability. Since this drive combines 7,200rpm platters and high density data, computers outfitted with this drive will be able to read data extraordinarily fast.", DriveTypeEnum.HARD_DISK_DRIVE, BigDecimal.valueOf(45));
                            break;
                        case Sabrent_Rocket_4_Plus:
                            drive = new DriveEntity(driveEnum, "2TB", 5, "Powered by Phison PS5018-E18 PCIe 4.0 x4 NVMe SSD controller and Micron’s 96L TLC flash, the Sabrent Rocket 4 Plus boasts some serious hardware that enabled it to shatters write speed records in our testing. Not only is it fast, with its black PCB and matching black PCB and copper tone heat spreader, but it’s also a very attractive M.2. At prices that undercut both WD and Samsung, it’s a great value for those looking to save some cash, but still, get that responsive PCIe 4.0 performance. Plus, it comes in a spacious 4TB capacity, unlike the WD and Samsung, too. But, bear in mind that at its lower price point it lacks AES 256-bit hardware encryption and comes with a 1-year warranty without registration within 90 days.", DriveTypeEnum.SOLID_STATE_DRIVE, BigDecimal.valueOf(909.99));
                            break;
                    }

                    driveRepository.save(drive);
                });
    }

    @Override
    public DriveEntity findDriveByName(DriveEnum driveEnum) {
        return driveRepository.findByName(driveEnum);
    }
}

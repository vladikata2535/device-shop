package computer.shop.service.impl;

import computer.shop.models.entity.enumEntity.RandomAccessMemoryEntity;
import computer.shop.models.entity.enums.componentsEnums.RandomAccessMemoryEnum;
import computer.shop.repository.RandomAccessMemoryRepository;
import computer.shop.service.RandomAccessMemoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class RandomAccessMemoryServiceImpl implements RandomAccessMemoryService {

    private final RandomAccessMemoryRepository randomAccessMemoryRepository;

    public RandomAccessMemoryServiceImpl(RandomAccessMemoryRepository randomAccessMemoryRepository) {
        this.randomAccessMemoryRepository = randomAccessMemoryRepository;
    }

    @Override
    public void initializeRAM() {
        Arrays.stream(RandomAccessMemoryEnum.values())
                .forEach(randomAccessMemoryEnum -> {
                    RandomAccessMemoryEntity ram = null;

                    switch (randomAccessMemoryEnum) {
                        case G_Skill_Ripjaws_V_16GB_DDR4_2400MHz:
                            ram = new RandomAccessMemoryEntity(randomAccessMemoryEnum, "DDR4-2400MHz", 1.2, "2x 8 GB", "The G.Skill Ripjaws V is the second generation of DDR4 memory from G.Skill, and it's clear the company listened to the feedback and criticisms from the customers. This series is more affordable, faster, and has a less tacky heatsink than its predecessor. We found the 16GB Ripjaws V kit to be the best option for a decent capacity kit that features great performance right out of the box.", BigDecimal.valueOf(59.99));
                            break;
                        case Colorful_CVN_Guardian_16GB_DDR4_3200:
                            ram = new RandomAccessMemoryEntity(randomAccessMemoryEnum, "DDR4-3200MHz", 1.35, "2x 8 GB", "Okay, so RGB LEDs on your memory sticks don't make your rig go faster, but they can really tie the room together. Or at least your PC's overall aesthetic. And the Colorful CVN Guardian DDR4, at just $95 for a dual-channel 16GB kit, is probably the most affordable route into nailing that all-important RGB look for your gaming system.", BigDecimal.valueOf(79.99));
                            break;
                        case TEAM_XTREEM_ARGB_16GB_DDR4_3600MHz_C14:
                            ram = new RandomAccessMemoryEntity(randomAccessMemoryEnum, "DDR4-3600MHz", 1.35, "2x 8 GB", "With a CAS latency of 14, the Team Xtreem kit leads the way in low-latency RAM favored by gaming PCs, especially AMD Ryzen rigs. As such, it takes the top spot as our pick for the best RAM for gaming.", BigDecimal.valueOf(235));
                            break;
                        case G_Skill_Trident_Z_Neo_32GB_DDR4_3600MHz:
                            ram = new RandomAccessMemoryEntity(randomAccessMemoryEnum, "DDR4-3600MHz", 1.35, "2x 16 GB", "G.Skill’s Trident Z RGB RAM has been a mainstay of memory guide for years now, and it’s no surprise the company’s Trident Z Neo series has also earned a spot here. Like the original Trident Z RGB series, the Trident Z Neo comes equipped with brilliant RGB lighting done in a very tasteful manner. More importantly, the Neo series is optimized for AMD Ryzen builds, making this budget-friendly option the perfect choice for budget-conscious Ryzen PCs.", BigDecimal.valueOf(182));
                            break;
                        case G_Skill_Trident_Z_Royal_16GB_DDR4_4000MHz:
                            ram = new RandomAccessMemoryEntity(randomAccessMemoryEnum, "DDR4-4000MHz", 1.5, "2x 8 GB", "Known for superb binned memory and high-speed kits, G.Skill's Trident Z Royal blends 4,000MHz (effective) operation with a highly stylized design. These DIMMs are just asking to be put center-stage in a showpiece gaming PC build—and it would be far from a slouch either.", BigDecimal.valueOf(243));
                            break;
                        case Corsair_Dominator_Platinum_RGB_32GB_DDR4_3200MHz:
                            ram = new RandomAccessMemoryEntity(randomAccessMemoryEnum, "DDR4-3200MHz", 1.35, "2x 16 GB", "Corsair has outdone itself with the Dominator Platinum RGB. The original DDR4 kit has been our favorite high-end memory bundle for quite some time now. Its sleek exterior, patented DHX cooling technology, and unrivaled performance has made it a formidable flagship over the years, often topping our best RAM for gaming list. Now, the iconic Dominator Platinum is back with a stealthy new design and Corsair's new Capellix LED technology.", BigDecimal.valueOf(237));
                            break;
                    }
                    randomAccessMemoryRepository.save(ram);
                });
    }

    @Override
    public RandomAccessMemoryEntity findRamByName(RandomAccessMemoryEnum accessMemoryEnum) {
        return randomAccessMemoryRepository.findByName(accessMemoryEnum);
    }
}

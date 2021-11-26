package computer.shop.service.impl;

import computer.shop.models.entity.enumEntity.PowerSupplyEntity;
import computer.shop.models.entity.enums.componentsEnums.PowerSupplyEnum;
import computer.shop.repository.PowerSupplyRepository;
import computer.shop.service.PowerSupplyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class PowerSupplyServiceImpl implements PowerSupplyService {

    private final PowerSupplyRepository powerSupplyRepository;

    public PowerSupplyServiceImpl(PowerSupplyRepository powerSupplyRepository) {
        this.powerSupplyRepository = powerSupplyRepository;
    }

    @Override
    public void initializePowerSupplies() {
        Arrays.stream(PowerSupplyEnum.values())
                .forEach(powerSupplyEnum -> {

                    PowerSupplyEntity powerSupply = null;

                    switch (powerSupplyEnum) {
                        case Corsair_CX450:
                            powerSupply = new PowerSupplyEntity(powerSupplyEnum, 450, "Cybenetics A- (25-30 dBA - CWT) | Standard+ (35-40 dBA - Great Wall)", "120 mm rifle bearing fan (HA1225M12F-Z [CWT] or D12SM-12 [Great Wall])", "80 PLUS Bronze", 1, 5, "The lowest capacity member of Corsair's budget-oriented CX line is the CX450. All CX models are manufactured by two different OEMs: either Great Wall or Channel Well Technology (CWT), and each of which uses a distinct platform.", BigDecimal.valueOf(69.99));
                            break;
                        case Corsair_RM750x:
                            powerSupply = new PowerSupplyEntity(powerSupplyEnum, 750, "Cybenetics A- (25-30 dBA)", "140 mm Mag Lev fan (NR140ML)", "80 PLUS Gold", 4, 10, "The time has come for Corsair to make some changes to its popular RMx line of power supplies since the competition has gotten far tougher in this market segment from the likes of Seasonic Focus GX, XPG Core Reactor, Super Flower Leadex V, etc.", BigDecimal.valueOf(216.99));
                            break;
                        case Corsair_AX1600i:
                            powerSupply = new PowerSupplyEntity(powerSupplyEnum, 1600, "Cybenetics A (20-25 dBA)", "140 mm Fluid Dynamic Bearing Fan (NR140P)", "80 PLUS Titanium", 10, 10, "The Corsair AX1600i was the first desktop PSU to use its cutting-edge power supply technology, but even several years after its initial release, few other PSUs utilize it. In short, the AX1600i uses a totem-pole PFC converter, utilizing GaN MOSFETs, which can offer up to 99 percent efficiency compared to the 96% efficiency levels that the most advanced conventional APFC converters can deliver. Okay, those are the technical details, but what you really need to know is that this is about as efficient as power supplies get.", BigDecimal.valueOf(725));
                            break;
                        case XPG_Core_Reactor_650W:
                            powerSupply = new PowerSupplyEntity(powerSupplyEnum, 650, "Cybenetics A (20-25 dBA)", "120 mm Fluid Dynamic Bearing Fan (HA1225H12F-Z)", "80 PLUS Gold", 4, 10, "XPG rocked the boat with its Core Reactor line, an impressive set of PSUs using a competent platform provided by Channel Well Technology. XPG took the company's CSE platform and ensured it had exclusive rights, so you won't see another OEM using CSE (the platform's code name).", BigDecimal.valueOf(99.99));
                            break;
                        case Fractal_Design_Ion_SFX_650_Gold:
                            powerSupply = new PowerSupplyEntity(powerSupplyEnum, 650, "Cybenetics Standard+ (35-40 dBA)", "120 mm FDB Fan (S1201512HB)", "80 PLUS Gold", 4, 10, "More and more of the best power supply brands have expanded into the small form factor category recently, an area that has started to garner much greater interest from gamers too. A few years ago, SFF PSUs were niche products, but the introduction of elegant small chassis, and the desire for smaller systems, have increased the competition in this category.", BigDecimal.valueOf(126));
                            break;
                        case Seasonic_Prime_Titanium_TX_1000:
                            powerSupply = new PowerSupplyEntity(powerSupplyEnum, 1000, "Cybenetics A- (25-30 dBA)", "135 mm FDB Fan (HA13525M12F-Z)", "80 PLUS Titanium", 6, 12, "Seasonic hit the jackpot with its Prime platform, which starts from Gold efficiency and goes all the way up to Titanium. Several high-impact brands have already used Seasonic's base platform in their own PSUs, including Asus with its ROG Thor 1200W, Corsair's AX line, and Antec with its legendary Signature line.", BigDecimal.valueOf(299.99));
                            break;
                    }

                    powerSupplyRepository.save(powerSupply);

                });

    }

    @Override
    public PowerSupplyEntity findPowerSupplyByName(PowerSupplyEnum powerSupplyEnum) {
        return powerSupplyRepository.findByName(powerSupplyEnum);
    }
}

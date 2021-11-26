package computer.shop.service;

import computer.shop.models.entity.enumEntity.PowerSupplyEntity;
import computer.shop.models.entity.enums.componentsEnums.PowerSupplyEnum;

public interface PowerSupplyService {
    void initializePowerSupplies();

    PowerSupplyEntity findPowerSupplyByName(PowerSupplyEnum powerSupplyEnum);
}

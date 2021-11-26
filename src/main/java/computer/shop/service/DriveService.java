package computer.shop.service;

import computer.shop.models.entity.enumEntity.DriveEntity;
import computer.shop.models.entity.enums.componentsEnums.DriveEnum;
import computer.shop.models.entity.enums.componentsEnums.DriveTypeEnum;

public interface DriveService {
    void initializeDrives();

    DriveEntity findDriveByName(DriveEnum driveEnum);
}

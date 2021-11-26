package computer.shop.service;

import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.service.SmartphoneServiceModel;
import computer.shop.models.view.SmartphoneAddOfferViewModel;
import computer.shop.models.view.SmartphoneWarehouseViewModel;

import java.util.List;

public interface SmartphoneService {
    void addSmartphone(SmartphoneServiceModel serviceModel);

    List<SmartphoneAddOfferViewModel> getAllSmartphones();

    SmartphoneEntity findSmartphoneByModelName(String smartphone);

    List<SmartphoneWarehouseViewModel> getAllSmartphonesWarehouse();
}

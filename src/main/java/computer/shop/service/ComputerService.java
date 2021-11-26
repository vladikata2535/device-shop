package computer.shop.service;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.service.ComputerServiceModel;
import computer.shop.models.view.ComputerAddOfferViewModel;
import computer.shop.models.view.ComputerWarehouseViewModel;

import java.util.List;

public interface ComputerService {
    void addComputer(ComputerServiceModel serviceModel);

    List<ComputerAddOfferViewModel> getAllComputers();

    ComputerEntity findComputerByName(String computerName);

    List<ComputerWarehouseViewModel> getAllComputersWarehouse();
}

package computer.shop.web;

import computer.shop.models.view.ComputerWarehouseViewModel;
import computer.shop.models.view.SmartphoneWarehouseViewModel;
import computer.shop.service.ComputerService;
import computer.shop.service.SmartphoneService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseRestController {
    private final ComputerService computerService;
    private final SmartphoneService smartphoneService;

    public WarehouseRestController(ComputerService computerService, SmartphoneService smartphoneService) {
        this.computerService = computerService;
        this.smartphoneService = smartphoneService;
    }

    @GetMapping("/api/warehouse/computers")
    public ResponseEntity<List<ComputerWarehouseViewModel>> getComputers(){
        return ResponseEntity.ok(computerService.getAllComputersWarehouse());
    }

    @GetMapping("/api/warehouse/smartphones")
    public ResponseEntity<List<SmartphoneWarehouseViewModel>> getSmartphones(){
        return ResponseEntity.ok(smartphoneService.getAllSmartphonesWarehouse());
    }
}

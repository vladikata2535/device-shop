package computer.shop.service.impl;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.ComputerOfferEntity;
import computer.shop.models.service.ComputerServiceModel;
import computer.shop.models.view.ComputerAddOfferViewModel;
import computer.shop.models.view.ComputerWarehouseViewModel;
import computer.shop.repository.*;
import computer.shop.service.*;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComputerServiceImpl implements ComputerService {

    private final ModelMapper modelMapper;
    private final ComputerRepository computerRepository;
    private final ComputerOfferRepository computerOfferRepository;
    private final DriveService driveService;
    private final PowerSupplyService powerSupplyService;
    private final ProcessorService processorService;
    private final RandomAccessMemoryService randomAccessMemoryService;
    private final VideoCardService videoCardService;

    public ComputerServiceImpl(ModelMapper modelMapper, ComputerRepository computerRepository, ComputerOfferRepository computerOfferRepository, DriveService driveService, PowerSupplyService powerSupplyService, ProcessorService processorService, RandomAccessMemoryService randomAccessMemoryService, VideoCardService videoCardService) {
        this.modelMapper = modelMapper;
        this.computerRepository = computerRepository;
        this.computerOfferRepository = computerOfferRepository;
        this.driveService = driveService;
        this.powerSupplyService = powerSupplyService;
        this.processorService = processorService;
        this.randomAccessMemoryService = randomAccessMemoryService;
        this.videoCardService = videoCardService;
    }

    @Override
    public void addComputer(ComputerServiceModel serviceModel) {
        ComputerEntity computer = modelMapper.map(serviceModel, ComputerEntity.class);
        computer.setDrive(driveService.findDriveByName(serviceModel.getDrive()));
        computer.setPowerSupply(powerSupplyService.findPowerSupplyByName(serviceModel.getPowerSupply()));
        computer.setProcessor(processorService.findProcessorByName(serviceModel.getProcessor()));
        computer.setRam(randomAccessMemoryService.findRamByName(serviceModel.getRam()));
        computer.setVideoCard(videoCardService.findVideoCardByName(serviceModel.getVideoCard()));
        computer.setPublished(false);

        List<BigDecimal> componentsPrices = List.of(computer.getDrive().getPrice(), computer.getPowerSupply().getPrice()
                , computer.getProcessor().getPrice(), computer.getRam().getPrice(), computer.getVideoCard().getPrice());

        computer.setPrice(componentsPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

        computerRepository.save(computer);
    }

    @Override
    public List<ComputerAddOfferViewModel> getAllComputers() {
        return computerRepository
                .findAll()
                .stream()
                .filter(computerEntity -> !computerEntity.isPublished())
                .map(computerEntity -> modelMapper.map(computerEntity, ComputerAddOfferViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComputerEntity findComputerByName(String computerName) {
        return computerRepository.findByName(computerName).orElseThrow(() -> new ObjectNotFoundException("Computer entity not found!"));
    }

    @Override
    public List<ComputerWarehouseViewModel> getAllComputersWarehouse() {
        return computerRepository
                .findAll()
                .stream()
                .map(computerEntity -> {
                    ComputerWarehouseViewModel viewModel = modelMapper.map(computerEntity, ComputerWarehouseViewModel.class);
                    viewModel.setPublished(computerEntity.isPublished());

                    return viewModel;
                })
                .collect(Collectors.toList());
    }
}

package computer.shop.service.impl;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.ComputerOfferEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.service.ComputerOfferServiceModel;
import computer.shop.models.view.ComputerOfferDetailsView;
import computer.shop.models.view.ComputerOfferViewModel;
import computer.shop.repository.ComputerOfferRepository;
import computer.shop.repository.ComputerRepository;
import computer.shop.service.ComputerOfferService;
import computer.shop.service.ComputerService;
import computer.shop.service.UserService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ComputerOfferServiceImpl implements ComputerOfferService {

    private final ComputerOfferRepository computerOfferRepository;
    private final ComputerRepository computerRepository;
    private final ModelMapper modelMapper;
    private final ComputerService computerService;
    private final UserService userService;

    public ComputerOfferServiceImpl(ComputerOfferRepository computerOfferRepository, ComputerRepository computerRepository, ModelMapper modelMapper, ComputerService computerService, UserService userService) {
        this.computerOfferRepository = computerOfferRepository;
        this.computerRepository = computerRepository;
        this.modelMapper = modelMapper;
        this.computerService = computerService;
        this.userService = userService;
    }

    @Override
    public void addComputerOffer(ComputerOfferServiceModel serviceModel, Principal principal) {
        ComputerEntity computer = computerService.findComputerByName(serviceModel.getComputer());
        UserEntity user = userService.findUserByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        computer.setPublished(true);
        computerRepository.save(computer);

        ComputerOfferEntity computerOfferEntity = modelMapper.map(serviceModel, ComputerOfferEntity.class);

        computerOfferEntity
                .setSeller(user)
                .setComputer(computer)
                .setPrice(computer.getPrice());

        computerOfferRepository.save(computerOfferEntity);
    }

    @Override
    public List<ComputerOfferViewModel> getAllComputersCatalog() {
        return computerOfferRepository
                .findAll()
                .stream()
                .map(computerOfferEntity -> {
                    ComputerOfferViewModel computerOfferViewModel = modelMapper.map(computerOfferEntity, ComputerOfferViewModel.class);

                    computerOfferViewModel.setReviews((long) new Random().nextInt(10000 - 1) + 1);
                    computerOfferViewModel.setComputerName(computerOfferEntity.getComputer().getName());

                    return computerOfferViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ComputerOfferDetailsView findOfferById(Long id, String name) {

        return  computerOfferRepository
                .findById(id)
                .map(computerOfferEntity -> {
                    ComputerOfferDetailsView computerOfferDetailsView = modelMapper.map(computerOfferEntity, ComputerOfferDetailsView.class);

                    computerOfferDetailsView.setSellerFullName(computerOfferEntity.getSeller().getFirstName() + " " + computerOfferEntity.getSeller().getLastName());
                    computerOfferDetailsView.setComputerName(computerOfferEntity.getComputer().getName());
                    computerOfferDetailsView.setComputerDescription(computerOfferEntity.getComputer().getDescription());
                    computerOfferDetailsView.setComputerManufacturedOn(computerOfferEntity.getComputer().getManufacturedOn());
                    computerOfferDetailsView.setComputerBox(computerOfferEntity.getComputer().getComputerBox().name());
                    computerOfferDetailsView.setComputerVideoCard(computerOfferEntity.getComputer().getVideoCard().getName().name());
                    computerOfferDetailsView.setComputerProcessor(computerOfferEntity.getComputer().getProcessor().getName().name());
                    computerOfferDetailsView.setComputerRam(computerOfferEntity.getComputer().getRam().getName().name());
                    computerOfferDetailsView.setComputerPowerSupply(computerOfferEntity.getComputer().getPowerSupply().getName().name());
                    computerOfferDetailsView.setComputerDrive(computerOfferEntity.getComputer().getDrive().getName().name());
                    computerOfferDetailsView.setComputerDriveTypeName(computerOfferEntity.getComputer().getDrive().getDriveType().name());


                    return computerOfferDetailsView;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Computer offer with id "+ id + " not found"));
    }
}

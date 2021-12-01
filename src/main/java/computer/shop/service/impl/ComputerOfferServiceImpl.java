package computer.shop.service.impl;

import computer.shop.models.entity.ComputerEntity;
import computer.shop.models.entity.ComputerOfferEntity;
import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.service.ComputerOfferServiceModel;
import computer.shop.models.service.ComputerUpdateServiceModel;
import computer.shop.models.view.ComputerOfferDetailsView;
import computer.shop.models.view.ComputerOfferViewModel;
import computer.shop.repository.ComputerOfferRepository;
import computer.shop.repository.ComputerRepository;
import computer.shop.repository.UserRepository;
import computer.shop.service.ComputerOfferService;
import computer.shop.service.ComputerService;
import computer.shop.service.UserService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ComputerOfferServiceImpl implements ComputerOfferService {

    private final ComputerOfferRepository computerOfferRepository;
    private final ComputerRepository computerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ComputerService computerService;

    public ComputerOfferServiceImpl(ComputerOfferRepository computerOfferRepository, ComputerRepository computerRepository, UserRepository userRepository, ModelMapper modelMapper, ComputerService computerService) {
        this.computerOfferRepository = computerOfferRepository;
        this.computerRepository = computerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.computerService = computerService;
    }

    @Override
    public void addComputerOffer(ComputerOfferServiceModel serviceModel, Principal principal) {
        ComputerEntity computer = computerService.findComputerByName(serviceModel.getComputer());
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

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
    public ComputerOfferDetailsView findOfferById(Long id) {

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

    @Override
    public double findOfferPriceById(Long offerId) {
        return computerOfferRepository
                .findById(offerId)
                .map(computerOfferEntity -> computerOfferEntity.getPrice().doubleValue())
                .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + offerId + " not found!"));
    }

    @Override
    public void buyProduct(Long offerId, String couponName, Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        ComputerOfferEntity offerEntity = computerOfferRepository.findById(offerId).orElseThrow(() -> new ObjectNotFoundException("Computer offer entity not found"));
        Long computerId = offerEntity.getComputer().getId();

        double price = offerEntity.getPrice().doubleValue();
        double balance = user.getBalance().doubleValue();

        if(couponName != null){
            double percentage = Double.parseDouble(couponName.substring(0,2));
            price = price - (price * (percentage / 100));
        }

        user.setBalance(BigDecimal.valueOf(balance - price));

        for(CouponEntity coupon : user.getActiveCoupons()){
            if(coupon.getPercentage().label.equals(Integer.parseInt(couponName.substring(0,2)))){
                user.getActiveCoupons().remove(coupon);
                break;
            }
        }
        userRepository.save(user);

        computerOfferRepository.deleteById(offerId);
        computerRepository.deleteById(computerId);

    }

    @Override
    public void deleteOffer(Long id) {
        ComputerOfferEntity offer = computerOfferRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Computer offer with id "+ id + " not found"));
        ComputerEntity computer = computerRepository.findById(offer.getComputer().getId()).orElseThrow(() -> new ObjectNotFoundException("Computer with id "+ offer.getComputer().getId() + " not found"));

        computer.setPublished(false);
        computerRepository.save(computer);

        computerOfferRepository.deleteById(id);
    }

    @Override
    public ComputerUpdateServiceModel findOfferForEdit(Long id) {
        return  computerOfferRepository
                .findById(id)
                .map(computerOfferEntity -> {
                    ComputerUpdateServiceModel model = modelMapper.map(computerOfferEntity, ComputerUpdateServiceModel.class);
                    model.setComputerName(computerOfferEntity.getComputer().getName());
                    return model;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " not found!"));
    }

    @Override
    public void updateComputer(ComputerUpdateServiceModel computerUpdateServiceModel, Principal principal, String oldComputerName) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        ComputerOfferEntity computerOfferEntity = computerOfferRepository
                .findById(computerUpdateServiceModel.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + computerUpdateServiceModel.getId() + " not found!"));

        ComputerEntity oldComputer = computerRepository.findByName(oldComputerName).orElseThrow(() -> new ObjectNotFoundException("Computer entity not found!"));
        oldComputer.setPublished(false);
        ComputerEntity computerEntity = computerRepository.findByName(computerUpdateServiceModel.getComputerName()).orElseThrow(() -> new ObjectNotFoundException("Computer entity not found!"));
        computerEntity.setPublished(true);

        computerRepository.save(oldComputer);
        computerRepository.save(computerEntity);

        computerOfferEntity
                .setComputer(computerEntity)
                .setPrice(computerUpdateServiceModel.getPrice())
                .setSeller(user)
                .setCreatedOn(computerUpdateServiceModel.getCreatedOn())
                .setDescription(computerUpdateServiceModel.getDescription())
                .setName(computerUpdateServiceModel.getName());

        computerOfferRepository.save(computerOfferEntity);
    }

    @Override
    public BigDecimal findPriceByComputerName(String computerName) {
        return computerRepository
                .findByName(computerName)
                .orElseThrow(() -> new ObjectNotFoundException("Computer entity not found!"))
                .getPrice();
    }

    @Override
    public String findComputerName(Long id) {
        return computerOfferRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " not found!"))
                .getComputer()
                .getName();
    }
}

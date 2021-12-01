package computer.shop.service.impl;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.entity.SmartphoneOfferEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.models.service.SmartphoneUpdateServiceModel;
import computer.shop.models.view.SmartphoneOfferDetailsView;
import computer.shop.models.view.SmartphoneOfferViewModel;
import computer.shop.repository.SmartphoneOfferRepository;
import computer.shop.repository.SmartphoneRepository;
import computer.shop.repository.UserRepository;
import computer.shop.service.SmartphoneOfferService;
import computer.shop.service.SmartphoneService;
import computer.shop.service.UserService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SmartphoneOfferServiceImpl implements SmartphoneOfferService {

    private final SmartphoneOfferRepository smartphoneOfferRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ModelMapper modelMapper;
    private final SmartphoneService smartphoneService;
    private final UserRepository userRepository;

    public SmartphoneOfferServiceImpl(SmartphoneOfferRepository smartphoneOfferRepository, SmartphoneRepository smartphoneRepository, ModelMapper modelMapper, SmartphoneService smartphoneService, UserRepository userRepository) {
        this.smartphoneOfferRepository = smartphoneOfferRepository;
        this.smartphoneRepository = smartphoneRepository;
        this.modelMapper = modelMapper;
        this.smartphoneService = smartphoneService;
        this.userRepository = userRepository;
    }

    @Override
    public void addSmartphoneOffer(SmartphoneOfferServiceModel serviceModel, Principal principal) {
        SmartphoneEntity smartphone = smartphoneService.findSmartphoneByModelName(serviceModel.getSmartphone());
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new ObjectNotFoundException("User not found!"));

        SmartphoneOfferEntity smartphoneOfferEntity = modelMapper.map(serviceModel, SmartphoneOfferEntity.class);

        smartphone.setPublished(true);
        smartphoneRepository.save(smartphone);

        smartphoneOfferEntity
                .setSeller(user)
                .setSmartphone(smartphone)
                .setPrice(smartphone.getPrice());

        smartphoneOfferRepository.save(smartphoneOfferEntity);
    }

    @Override
    public List<SmartphoneOfferViewModel> getAllSmartphonesCatalog() {
        return smartphoneOfferRepository
                .findAll()
                .stream()
                .map(smartphoneOfferEntity -> {
                    SmartphoneOfferViewModel smartphoneOfferViewModel = modelMapper.map(smartphoneOfferEntity, SmartphoneOfferViewModel.class);

                    smartphoneOfferViewModel.setReviews((long) new Random().nextInt(10000 - 1) + 1);
                    smartphoneOfferViewModel.setModelName(smartphoneOfferEntity.getSmartphone().getModelName());

                    return smartphoneOfferViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SmartphoneOfferDetailsView findOfferById(Long id) {
        return smartphoneOfferRepository
                .findById(id)
                .map(smartphoneOfferEntity -> {
                    SmartphoneOfferDetailsView view = modelMapper.map(smartphoneOfferEntity, SmartphoneOfferDetailsView.class);
                    view.setSellerFullName(smartphoneOfferEntity.getSeller().getFirstName() + " " + smartphoneOfferEntity.getSeller().getLastName());

                    return view;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id "+ id + " not found"));
    }

    @Override
    public void deleteOffer(Long id) {
        SmartphoneOfferEntity smartphoneOfferEntity = smartphoneOfferRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id "+ id + " not found"));
        SmartphoneEntity smartphone = smartphoneRepository.findById(smartphoneOfferEntity.getSmartphone().getId()).orElseThrow(() -> new ObjectNotFoundException("Smartphone with id "+ smartphoneOfferEntity.getSmartphone().getId() + " not found"));

        smartphone.setPublished(false);
        smartphoneRepository.save(smartphone);

        smartphoneOfferRepository.deleteById(id);
    }

    @Override
    public double findOfferPriceById(Long smartphoneOfferId) {
        return smartphoneOfferRepository
                .findById(smartphoneOfferId)
                .map(smartphoneOfferEntity -> smartphoneOfferEntity.getPrice().doubleValue())
                .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + smartphoneOfferId + " not found!"));
    }

    @Override
    public void buyProduct(Long id, String couponName, Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        SmartphoneOfferEntity smartphoneOfferEntity = smartphoneOfferRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id "+ id + " not found"));
        Long smartphoneId = smartphoneOfferEntity.getSmartphone().getId();

        double price = smartphoneOfferEntity.getPrice().doubleValue();
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

        smartphoneOfferRepository.deleteById(id);
        smartphoneRepository.deleteById(smartphoneId);
    }

    @Override
    public SmartphoneUpdateServiceModel findSmartphoneForEdit(Long id) {
        return smartphoneOfferRepository
                .findById(id)
                .map(smartphoneOfferEntity -> {
                    SmartphoneUpdateServiceModel serviceModel = modelMapper.map(smartphoneOfferEntity, SmartphoneUpdateServiceModel.class);
                    serviceModel.setSmartphoneModelName(smartphoneOfferEntity.getSmartphone().getModelName());

                    return serviceModel;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id " + id + " not found!"));
    }

    @Override
    public String findSmartphoneName(Long id) {
        return smartphoneOfferRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id " + id + " not found!"))
                .getSmartphone()
                .getModelName();
    }

    @Override
    public BigDecimal findPriceBySmartphoneName(String smartphoneModelName) {
        return smartphoneRepository
                .findByModelName(smartphoneModelName)
                .orElseThrow(() -> new ObjectNotFoundException("Smartphone entity not found!"))
                .getPrice();
    }

    @Override
    public void updateSmartphone(SmartphoneUpdateServiceModel smartphoneUpdateServiceModel, Principal principal, String oldSmartphoneName) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        SmartphoneOfferEntity smartphoneOfferEntity = smartphoneOfferRepository
                .findById(smartphoneUpdateServiceModel.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id " + smartphoneUpdateServiceModel.getId() + " not found!"));

        SmartphoneEntity oldSmartphone = smartphoneRepository.findByModelName(oldSmartphoneName).orElseThrow(() -> new ObjectNotFoundException("Smartphone entity not found!"));
        oldSmartphone.setPublished(false);
        SmartphoneEntity smartphone = smartphoneRepository.findByModelName(smartphoneUpdateServiceModel.getSmartphoneModelName()).orElseThrow(() -> new ObjectNotFoundException("Smartphone entity not found!"));
        smartphone.setPublished(true);

        smartphoneRepository.save(oldSmartphone);
        smartphoneRepository.save(smartphone);

        smartphoneOfferEntity
                .setSmartphone(smartphone)
                .setPrice(smartphoneUpdateServiceModel.getPrice())
                .setSeller(user)
                .setCreatedOn(smartphoneUpdateServiceModel.getCreatedOn())
                .setDescription(smartphoneUpdateServiceModel.getDescription())
                .setName(smartphoneUpdateServiceModel.getName());

        smartphoneOfferRepository.save(smartphoneOfferEntity);
    }
}

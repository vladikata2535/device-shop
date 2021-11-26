package computer.shop.service.impl;

import computer.shop.models.entity.SmartphoneEntity;
import computer.shop.models.entity.SmartphoneOfferEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.models.view.SmartphoneOfferDetailsView;
import computer.shop.models.view.SmartphoneOfferViewModel;
import computer.shop.repository.SmartphoneOfferRepository;
import computer.shop.repository.SmartphoneRepository;
import computer.shop.service.SmartphoneOfferService;
import computer.shop.service.SmartphoneService;
import computer.shop.service.UserService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SmartphoneOfferServiceImpl implements SmartphoneOfferService {

    private final SmartphoneOfferRepository smartphoneOfferRepository;
    private final SmartphoneRepository smartphoneRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final SmartphoneService smartphoneService;

    public SmartphoneOfferServiceImpl(SmartphoneOfferRepository smartphoneOfferRepository, SmartphoneRepository smartphoneRepository, ModelMapper modelMapper, UserService userService, SmartphoneService smartphoneService) {
        this.smartphoneOfferRepository = smartphoneOfferRepository;
        this.smartphoneRepository = smartphoneRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.smartphoneService = smartphoneService;
    }

    @Override
    public void addSmartphoneOffer(SmartphoneOfferServiceModel serviceModel, Principal principal) {
        SmartphoneEntity smartphone = smartphoneService.findSmartphoneByModelName(serviceModel.getSmartphone());
        UserEntity user = userService.findUserByName(principal.getName()).orElseThrow(() -> new ObjectNotFoundException("User not found!"));

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
    public SmartphoneOfferDetailsView findOfferById(Long id, String name) {
        return smartphoneOfferRepository
                .findById(id)
                .map(smartphoneOfferEntity -> {
                    SmartphoneOfferDetailsView view = modelMapper.map(smartphoneOfferEntity, SmartphoneOfferDetailsView.class);
                    view.setSellerFullName(smartphoneOfferEntity.getSeller().getFirstName() + " " + smartphoneOfferEntity.getSeller().getLastName());

                    return view;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Smartphone offer with id "+ id + " not found"));
    }
}

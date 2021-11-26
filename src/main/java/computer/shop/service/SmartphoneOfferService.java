package computer.shop.service;

import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.models.view.SmartphoneOfferDetailsView;
import computer.shop.models.view.SmartphoneOfferViewModel;

import java.security.Principal;
import java.util.List;

public interface SmartphoneOfferService {
    void addSmartphoneOffer(SmartphoneOfferServiceModel serviceModel, Principal principal);

    List<SmartphoneOfferViewModel> getAllSmartphonesCatalog();

    SmartphoneOfferDetailsView findOfferById(Long id, String name);
}

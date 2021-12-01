package computer.shop.service;

import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.models.service.SmartphoneUpdateServiceModel;
import computer.shop.models.view.SmartphoneOfferDetailsView;
import computer.shop.models.view.SmartphoneOfferViewModel;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface SmartphoneOfferService {
    void addSmartphoneOffer(SmartphoneOfferServiceModel serviceModel, Principal principal);

    List<SmartphoneOfferViewModel> getAllSmartphonesCatalog();

    SmartphoneOfferDetailsView findOfferById(Long id);

    void deleteOffer(Long id);

    double findOfferPriceById(Long smartphoneOfferId);

    void buyProduct(Long id, String couponName, Principal principal);

    SmartphoneUpdateServiceModel findSmartphoneForEdit(Long id);

    String findSmartphoneName(Long id);

    BigDecimal findPriceBySmartphoneName(String smartphoneModelName);

    void updateSmartphone(SmartphoneUpdateServiceModel smartphoneUpdateServiceModel, Principal principal, String smartphoneName);
}

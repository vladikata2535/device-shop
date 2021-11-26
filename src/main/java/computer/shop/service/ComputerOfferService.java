package computer.shop.service;

import computer.shop.models.service.ComputerOfferServiceModel;
import computer.shop.models.view.ComputerOfferDetailsView;
import computer.shop.models.view.ComputerOfferViewModel;

import java.security.Principal;
import java.util.List;

public interface ComputerOfferService {
    void addComputerOffer(ComputerOfferServiceModel serviceModel, Principal principal);

    List<ComputerOfferViewModel> getAllComputersCatalog();

    ComputerOfferDetailsView findOfferById(Long id, String name);
}

package computer.shop.web;

import computer.shop.models.binding.ComputerOfferAddBindingModel;
import computer.shop.models.binding.SmartphoneOfferAddBindingModel;
import computer.shop.models.service.ComputerOfferServiceModel;
import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.service.ComputerOfferService;
import computer.shop.service.ComputerService;
import computer.shop.service.SmartphoneOfferService;
import computer.shop.service.SmartphoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final ModelMapper modelMapper;
    private final ComputerOfferService computerOfferService;
    private final ComputerService computerService;
    private final SmartphoneOfferService smartphoneOfferService;
    private final SmartphoneService smartphoneService;

    public OfferController(ModelMapper modelMapper, ComputerOfferService computerOfferService, ComputerService computerService, SmartphoneOfferService smartphoneOfferService, SmartphoneService smartphoneService) {
        this.modelMapper = modelMapper;
        this.computerOfferService = computerOfferService;
        this.computerService = computerService;
        this.smartphoneOfferService = smartphoneOfferService;
        this.smartphoneService = smartphoneService;
    }

    @GetMapping("/add-computer-offer")
    public String addComputerOffer(Model model){

        model.addAttribute("computers",computerService.getAllComputers());

        return "add-computer-offer";
    }

    @PostMapping("/add-computer-offer")
    public String addComputerOfferConfirm(@Valid ComputerOfferAddBindingModel computerOfferAddBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes,
                                          Principal principal){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("computerOfferAddBindingModel",computerOfferAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.computerOfferAddBindingModel",bindingResult);

            return "redirect:/offers/add-computer-offer";
        }

        computerOfferService.addComputerOffer(modelMapper.map(computerOfferAddBindingModel, ComputerOfferServiceModel.class), principal);

        return "redirect:/computers/catalog";
    }

    @GetMapping("/add-smartphone-offer")
    public String addSmartphoneOffer(Model model){

        model.addAttribute("smartphones", smartphoneService.getAllSmartphones());

        return "add-smartphone-offer";
    }

    @PostMapping("/add-smartphone-offer")
    public String addSmartphoneOfferConfirm(@Valid SmartphoneOfferAddBindingModel smartphoneOfferAddBindingModel,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            Principal principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("smartphoneOfferAddBindingModel",smartphoneOfferAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.smartphoneOfferAddBindingModel",bindingResult);

            return "redirect:/offers/add-smartphone-offer";
        }

        smartphoneOfferService.addSmartphoneOffer(modelMapper.map(smartphoneOfferAddBindingModel, SmartphoneOfferServiceModel.class), principal);

        return "redirect:/smartphones/catalog";
    }

    @GetMapping("/computers/{id}/details")
    public String computerOfferDetails(@PathVariable Long id, Model model, Principal principal){

        model.addAttribute("computer", computerOfferService.findOfferById(id, principal.getName()));

        return "computer-details";
    }

    @GetMapping("/smartphones/{id}/details")
    public String smartphoneOfferDetails(@PathVariable Long id, Model model, Principal principal){
        model.addAttribute("smartphone", smartphoneOfferService.findOfferById(id, principal.getName()));

        return "smartphone-details";
    }

    @ModelAttribute
    public ComputerOfferAddBindingModel computerOfferAddBindingModel(){
        return new ComputerOfferAddBindingModel();
    }

    @ModelAttribute
    public SmartphoneOfferAddBindingModel smartphoneOfferAddBindingModel(){
        return new SmartphoneOfferAddBindingModel();
    }

}

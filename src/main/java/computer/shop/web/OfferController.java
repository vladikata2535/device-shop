package computer.shop.web;

import computer.shop.models.binding.ComputerOfferAddBindingModel;
import computer.shop.models.binding.CouponBuyBindingModel;
import computer.shop.models.binding.SmartphoneOfferAddBindingModel;
import computer.shop.models.service.ComputerOfferServiceModel;
import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.service.*;
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
    private final CouponService couponService;
    private final UserService userService;

    public OfferController(ModelMapper modelMapper, ComputerOfferService computerOfferService, ComputerService computerService, SmartphoneOfferService smartphoneOfferService, SmartphoneService smartphoneService, CouponService couponService, UserService userService) {
        this.modelMapper = modelMapper;
        this.computerOfferService = computerOfferService;
        this.computerService = computerService;
        this.smartphoneOfferService = smartphoneOfferService;
        this.smartphoneService = smartphoneService;
        this.couponService = couponService;
        this.userService = userService;
    }

    @GetMapping("/add-computer-offer")
    public String addComputerOffer(Model model) {

        model.addAttribute("computers", computerService.getAllComputers());

        return "add-computer-offer";
    }

    @PostMapping("/add-computer-offer")
    public String addComputerOfferConfirm(@Valid ComputerOfferAddBindingModel computerOfferAddBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes,
                                          Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("computerOfferAddBindingModel", computerOfferAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.computerOfferAddBindingModel", bindingResult);

            return "redirect:/offers/add-computer-offer";
        }

        computerOfferService.addComputerOffer(modelMapper.map(computerOfferAddBindingModel, ComputerOfferServiceModel.class), principal);

        return "redirect:/computers/catalog";
    }

    @GetMapping("/add-smartphone-offer")
    public String addSmartphoneOffer(Model model) {

        model.addAttribute("smartphones", smartphoneService.getAllSmartphones());

        return "add-smartphone-offer";
    }

    @PostMapping("/add-smartphone-offer")
    public String addSmartphoneOfferConfirm(@Valid SmartphoneOfferAddBindingModel smartphoneOfferAddBindingModel,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("smartphoneOfferAddBindingModel", smartphoneOfferAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.smartphoneOfferAddBindingModel", bindingResult);

            return "redirect:/offers/add-smartphone-offer";
        }

        smartphoneOfferService.addSmartphoneOffer(modelMapper.map(smartphoneOfferAddBindingModel, SmartphoneOfferServiceModel.class), principal);

        return "redirect:/smartphones/catalog";
    }

    @GetMapping("/computers/{id}/details")
    public String computerOfferDetails(@PathVariable Long id, Model model) {

        model.addAttribute("computer", computerOfferService.findOfferById(id));

        return "computer-details";
    }

    //TODO make delete and edit and make isAdminOrOwner method to check them
    //TODO make a table where every user can have String info about bought products and render them on the home page

    @GetMapping("/computers/{id}/buy")
    public String buy(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("couponsNames", couponService.findAllCurrentLoggedUserActiveCoupons(principal));
        model.addAttribute("offerDetails", computerOfferService.findOfferById(id));

        if (!model.containsAttribute("canUserBuy")) {
            model.addAttribute("canUserBuy", true);
        }

        return "computer-buy";
    }

    @PostMapping("/computers/{id}/buy")
    public String buyConfirm(@PathVariable Long id, @Valid CouponBuyBindingModel couponBuyBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        boolean canUserBuyProduct;

        if (bindingResult.hasErrors()) {
            canUserBuyProduct = userService.canUserBuyProduct(id, null, principal);
        } else {
            canUserBuyProduct = userService.canUserBuyProduct(id, couponBuyBindingModel.getCouponName(), principal);
        }

        if (!canUserBuyProduct) {
            redirectAttributes.addFlashAttribute("canUserBuy", false);
            redirectAttributes.addFlashAttribute("couponBuyBindingModel", couponBuyBindingModel);

            return "redirect:/offers/computers/" + id + "/buy";
        }

        if (bindingResult.hasErrors()) {
            computerOfferService.buyProduct(id, null, principal);
        } else {
            computerOfferService.buyProduct(id, couponBuyBindingModel.getCouponName(), principal);
        }

        return "redirect:/home";
    }

    @GetMapping("/smartphones/{id}/details")
    public String smartphoneOfferDetails(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("smartphone", smartphoneOfferService.findOfferById(id, principal.getName()));

        return "smartphone-details";
    }

    @ModelAttribute
    public ComputerOfferAddBindingModel computerOfferAddBindingModel() {
        return new ComputerOfferAddBindingModel();
    }

    @ModelAttribute
    public SmartphoneOfferAddBindingModel smartphoneOfferAddBindingModel() {
        return new SmartphoneOfferAddBindingModel();
    }

}

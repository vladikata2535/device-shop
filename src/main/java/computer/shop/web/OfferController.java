package computer.shop.web;

import computer.shop.models.binding.*;
import computer.shop.models.service.ComputerOfferServiceModel;
import computer.shop.models.service.ComputerUpdateServiceModel;
import computer.shop.models.service.SmartphoneOfferServiceModel;
import computer.shop.models.service.SmartphoneUpdateServiceModel;
import computer.shop.service.*;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

        smartphoneOfferService.addSmartphoneOffer(modelMapper.map(smartphoneOfferAddBindingModel, SmartphoneOfferServiceModel.class), principal.getName());

        return "redirect:/smartphones/catalog";
    }

    @GetMapping("/computers/{id}/details")
    public String computerOfferDetails(@PathVariable Long id, Model model) {

        model.addAttribute("computer", computerOfferService.findOfferById(id));

        return "computer-details";
    }

    @PreAuthorize("@computerOfferServiceImpl.isAdmin(#principal.name, #id)")
    @DeleteMapping("/computers/{id}/delete")
    public String deleteComputerOffer(@PathVariable Long id, Principal principal){

        computerOfferService.deleteOffer(id);

        return "redirect:/computers/catalog";
    }

    @PreAuthorize("@computerOfferServiceImpl.isAdmin(#principal.name, #id)")
    @GetMapping("/computers/{id}/edit")
    public String editComputerOffer(@PathVariable Long id, Model model, Principal principal){

        ComputerUpdateServiceModel computerUpdateServiceModel = computerOfferService.findOfferForEdit(id);
        ComputerUpdateBindingModel computerUpdateBindingModel = modelMapper.map(computerUpdateServiceModel, ComputerUpdateBindingModel.class);

        model.addAttribute("computers", computerService.getAllComputers());
        model.addAttribute("currentComputerName", computerOfferService.findComputerName(id));
        model.addAttribute("computerUpdateBindingModel", computerUpdateBindingModel);

        return "computer-update";
    }

    @PreAuthorize("@computerOfferServiceImpl.isAdmin(#principal.name, #id)")
    @GetMapping("/computers/{id}/edit/errors")
    public String editComputerOfferErrors(@PathVariable Long id, Model model, Principal principal){

        model.addAttribute("computers", computerService.getAllComputers());
        model.addAttribute("currentComputerName", computerOfferService.findComputerName(id));

        return "computer-update";
    }

    @PreAuthorize("@computerOfferServiceImpl.isAdmin(#principal.name, #id)")
    @PatchMapping("/computers/{id}/edit")
    public String editComputerOfferConfirm(@PathVariable Long id, @Valid ComputerUpdateBindingModel computerUpdateBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("computerUpdateBindingModel", computerUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.computerUpdateBindingModel", bindingResult);

            return "redirect:/offers/computers/" + id + "/edit/errors";
        }

        ComputerUpdateServiceModel computerUpdateServiceModel = modelMapper.map(computerUpdateBindingModel, ComputerUpdateServiceModel.class);
        computerUpdateServiceModel.setPrice(computerOfferService.findPriceByComputerName(computerUpdateBindingModel.getComputerName()));
        computerUpdateServiceModel.setId(id);

        computerOfferService.updateComputer(computerUpdateServiceModel, principal, computerOfferService.findComputerName(id));

        return "redirect:/offers/computers/" + id + "/details";
    }

    @GetMapping("/computers/{id}/buy")
    public String buyComputer(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("couponsNames", couponService.findAllCurrentLoggedUserActiveCoupons(principal.getName()));
        model.addAttribute("offerDetails", computerOfferService.findOfferById(id));

        if (!model.containsAttribute("canUserBuy")) {
            model.addAttribute("canUserBuy", true);
        }

        return "computer-buy";
    }

    @PostMapping("/computers/{id}/buy")
    public String buyComputerConfirm(@PathVariable Long id, @Valid CouponBuyBindingModel couponBuyBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        boolean canUserBuyProduct;

        if (bindingResult.hasErrors()) {
            canUserBuyProduct = userService.canUserBuyProduct(null, id, null, principal.getName());
        } else {
            canUserBuyProduct = userService.canUserBuyProduct(null, id, couponBuyBindingModel.getCouponName(), principal.getName());
        }

        if (!canUserBuyProduct) {
            redirectAttributes.addFlashAttribute("canUserBuy", false);
            redirectAttributes.addFlashAttribute("couponBuyBindingModel", couponBuyBindingModel);

            return "redirect:/offers/computers/" + id + "/buy";
        }

        if (bindingResult.hasErrors()) {
            computerOfferService.buyProduct(id, null, principal.getName());
        } else {
            computerOfferService.buyProduct(id, couponBuyBindingModel.getCouponName(), principal.getName());
        }

        return "redirect:/home";
    }

    @GetMapping("/smartphones/{id}/details")
    public String smartphoneOfferDetails(@PathVariable Long id, Model model) {
        model.addAttribute("smartphone", smartphoneOfferService.findOfferById(id));

        return "smartphone-details";
    }

    @PreAuthorize("@smartphoneOfferServiceImpl.isAdmin(#principal.name, #id)")
    @DeleteMapping("/smartphones/{id}/delete")
    public String deleteSmartphoneOffer(@PathVariable Long id, Principal principal){

        smartphoneOfferService.deleteOffer(id);

        return "redirect:/smartphones/catalog";
    }

    @GetMapping("/smartphones/{id}/buy")
    public String buySmartphone(@PathVariable Long id, Model model, Principal principal){

        model.addAttribute("couponsNames", couponService.findAllCurrentLoggedUserActiveCoupons(principal.getName()));
        model.addAttribute("offerDetails", smartphoneOfferService.findOfferById(id));

        if (!model.containsAttribute("canUserBuy")) {
            model.addAttribute("canUserBuy", true);
        }

        return "smartphone-buy";
    }

    @PostMapping("/smartphones/{id}/buy")
    public String buySmartphoneConfirm(@PathVariable Long id, @Valid CouponBuyBindingModel couponBuyBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){

        boolean canUserBuyProduct;

        if(bindingResult.hasErrors()){
            canUserBuyProduct = userService.canUserBuyProduct(id, null, null, principal.getName());
        }else {
            canUserBuyProduct = userService.canUserBuyProduct(id, null, couponBuyBindingModel.getCouponName(), principal.getName());
        }

        if (!canUserBuyProduct) {
            redirectAttributes.addFlashAttribute("canUserBuy", false);
            redirectAttributes.addFlashAttribute("couponBuyBindingModel", couponBuyBindingModel);

            return "redirect:/offers/smartphones/" + id + "/buy";
        }

        if (bindingResult.hasErrors()) {
            smartphoneOfferService.buyProduct(id, null, principal);
        } else {
            smartphoneOfferService.buyProduct(id, couponBuyBindingModel.getCouponName(), principal);
        }

        return "redirect:/home";
    }

    @PreAuthorize("@smartphoneOfferServiceImpl.isAdmin(#principal.name, #id)")
    @GetMapping("/smartphones/{id}/edit")
    public String editSmartphoneOffer(@PathVariable Long id, Model model, Principal principal){

        SmartphoneUpdateServiceModel serviceModel = smartphoneOfferService.findSmartphoneForEdit(id);
        SmartphoneUpdateBindingModel smartphoneUpdateBindingModel = modelMapper.map(serviceModel, SmartphoneUpdateBindingModel.class);

        model.addAttribute("smartphones", smartphoneService.getAllSmartphones());
        model.addAttribute("currentSmartphoneName", smartphoneOfferService.findSmartphoneName(id));
        model.addAttribute("smartphoneUpdateBindingModel", smartphoneUpdateBindingModel);

        return "smartphone-update";
    }

    @PreAuthorize("@smartphoneOfferServiceImpl.isAdmin(#principal.name, #id)")
    @GetMapping("/smartphones/{id}/edit/errors")
    public String editSmartphoneOfferErrors(@PathVariable Long id, Model model, Principal principal){

        model.addAttribute("smartphones", smartphoneService.getAllSmartphones());
        model.addAttribute("currentSmartphoneName", smartphoneOfferService.findSmartphoneName(id));

        return "smartphone-update";
    }

    @PreAuthorize("@smartphoneOfferServiceImpl.isAdmin(#principal.name, #id)")
    @PatchMapping("/smartphones/{id}/edit")
    public String editSmartphoneOfferConfirm(@PathVariable Long id, @Valid SmartphoneUpdateBindingModel smartphoneUpdateBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("smartphoneUpdateBindingModel", smartphoneUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.smartphoneUpdateBindingModel", bindingResult);

            return "redirect:/offers/smartphones/" + id + "/edit/errors";
        }

        SmartphoneUpdateServiceModel smartphoneUpdateServiceModel = modelMapper.map(smartphoneUpdateBindingModel, SmartphoneUpdateServiceModel.class);
        smartphoneUpdateServiceModel.setPrice(smartphoneOfferService.findPriceBySmartphoneName(smartphoneUpdateBindingModel.getSmartphoneModelName()));
        smartphoneUpdateServiceModel.setId(id);

        smartphoneOfferService.updateSmartphone(smartphoneUpdateServiceModel, principal, smartphoneOfferService.findSmartphoneName(id));

        return "redirect:/offers/smartphones/" + id + "/details";
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

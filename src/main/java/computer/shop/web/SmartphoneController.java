package computer.shop.web;

import computer.shop.models.binding.SmartphoneAddBindingModel;
import computer.shop.models.service.SmartphoneServiceModel;
import computer.shop.service.SmartphoneOfferService;
import computer.shop.service.SmartphoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/smartphones")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;
    private final SmartphoneOfferService smartphoneOfferService;
    private final ModelMapper modelMapper;

    public SmartphoneController(SmartphoneService smartphoneService, SmartphoneOfferService smartphoneOfferService, ModelMapper modelMapper) {
        this.smartphoneService = smartphoneService;
        this.smartphoneOfferService = smartphoneOfferService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/catalog")
    public String smartphones(Model model){

        model.addAttribute("smartphonesCatalog", smartphoneOfferService.getAllSmartphonesCatalog());

        return "smartphones-catalog";
    }

    @GetMapping("/add-smartphone")
    public String add(){
        return "add-smartphone";
    }

    @PostMapping("/add-smartphone")
    public String addConfirm(@Valid SmartphoneAddBindingModel smartphoneAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("smartphoneAddBindingModel",smartphoneAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.smartphoneAddBindingModel",bindingResult);

            return "redirect:/smartphones/add-smartphone";
        }

            smartphoneService.addSmartphone(modelMapper.map(smartphoneAddBindingModel, SmartphoneServiceModel.class));

        return "redirect:/smartphones/catalog";
    }

    @ModelAttribute
    public SmartphoneAddBindingModel smartphoneAddBindingModel(){
        return new SmartphoneAddBindingModel();
    }

}

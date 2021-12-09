package computer.shop.web;

import computer.shop.models.binding.ComputerAddBindingModel;
import computer.shop.models.service.ComputerServiceModel;
import computer.shop.service.ComputerOfferService;
import computer.shop.service.ComputerService;
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
@RequestMapping("/computers")
public class ComputerController {

    private final ComputerService computerService;
    private final ComputerOfferService computerOfferService;
    private final ModelMapper modelMapper;

    public ComputerController(ComputerService computerService, ComputerOfferService computerOfferService, ModelMapper modelMapper) {
        this.computerService = computerService;
        this.computerOfferService = computerOfferService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/catalog")
    public String computers(Model model){

        model.addAttribute("computersCatalog", computerOfferService.getAllComputersCatalog());

        return "computers-catalog";
    }

    @GetMapping("/add-computer")
    public String add(){
        return "add-computer";
    }

    @PostMapping("/add-computer")
    public String addConfirm(@Valid ComputerAddBindingModel computerAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("computerAddBindingModel",computerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.computerAddBindingModel",bindingResult);

            return "redirect:/computers/add-computer";
        }

        computerService.addComputer(modelMapper.map(computerAddBindingModel, ComputerServiceModel.class));

        return "redirect:/computers/catalog";
    }

    @ModelAttribute
    public ComputerAddBindingModel computerAddBindingModel(){
        return new ComputerAddBindingModel();
    }
}

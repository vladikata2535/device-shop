package computer.shop.web;

import computer.shop.models.binding.UserRegisterBindingModel;
import computer.shop.models.service.UserServiceModel;
import computer.shop.service.CouponService;
import computer.shop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CouponService couponService;

    public UserController(UserService userService, ModelMapper modelMapper, CouponService couponService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.couponService = couponService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login-error")
    public String loginFailed(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("username",username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String register(Model model){

        if(!model.containsAttribute("isUserOrEmailOccupied")){
            model.addAttribute("isUserOrEmailOccupied", false);
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){

            redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",bindingResult);

            return "redirect:register";
        }

        boolean isUserExistingByEmailOrUsername = userService.isUserExistingByEmailOrUsername(userRegisterBindingModel.getEmail(),userRegisterBindingModel.getUsername());

        if(isUserExistingByEmailOrUsername){
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUserOrEmailOccupied", true);

            return "redirect:register";
        }

        userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:/";
    }

    @GetMapping("/my-coupons")
    public String myCoupons(Model model, Principal principal){

        model.addAttribute("notActiveUserCoupons", couponService.findAllCurrentLoggedUserNotActiveCoupons(principal.getName()));
        model.addAttribute("activeUserCoupons", couponService.findAllCurrentLoggedUserActiveCoupons(principal.getName()));

        return "my-coupons";
    }

    @GetMapping("/my-coupons/{id}/activate")
    public String activateCoupon(@PathVariable Long id, Principal principal){

        couponService.activateCoupon(id, principal.getName());

        return "redirect:/users/my-coupons";
    }

    @GetMapping("/warehouse")
    public String warehouse(){
        return "warehouse";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){

        model.addAttribute("info", userService.getUserInfo(principal.getName()));

        return "profile";
    }

    @GetMapping("/profile/add-money")
    public String addMoney(Principal principal){

        userService.addMoneyToUser(principal.getName());

        return "redirect:/users/profile";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }
}

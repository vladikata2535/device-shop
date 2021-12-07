package computer.shop.web;

import computer.shop.models.view.UserBalanceViewModel;
import computer.shop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoRestController {

    private final UserService userService;

    public UserInfoRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/info")
    public ResponseEntity<UserBalanceViewModel> getLoggedUserInfo(Principal principal){
        return ResponseEntity.ok(userService.getLoggedUserInfo(principal.getName()));
    }
}

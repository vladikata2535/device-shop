package computer.shop.service;

import computer.shop.models.entity.UserEntity;
import computer.shop.models.service.UserServiceModel;
import computer.shop.models.view.UserBalanceViewModel;
import computer.shop.models.view.UserViewModel;

import java.security.Principal;
import java.util.Optional;

public interface UserService {

    void initializeUsers();
    void registerUser(UserServiceModel serviceModel);

    Optional<UserEntity> findUserByName(String name);

    UserBalanceViewModel getLoggedUserInfo(Principal principal);

    boolean isUserExistingByEmailOrUsername(String email, String username);

    boolean canUserBuyProduct(Long offerId, String couponName, Principal principal);

    double getUserBalance(Principal principal);

    UserViewModel getUserInfo(Principal principal);

    void addMoneyToUser(Principal principal);
}

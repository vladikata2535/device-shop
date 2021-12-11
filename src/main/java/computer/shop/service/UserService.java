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

    UserBalanceViewModel getLoggedUserInfo(String name);

    boolean isUserExistingByEmailOrUsername(String email, String username);

    boolean canUserBuyProduct(Long smartphoneOfferId ,Long computerOfferId, String couponName, String name);

    double getUserBalance(String name);

    UserViewModel getUserInfo(String userName);

    void addMoneyToUser(String userName);
}

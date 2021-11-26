package computer.shop.service;

import computer.shop.models.entity.UserEntity;
import computer.shop.models.service.UserServiceModel;
import computer.shop.models.view.UserInfoViewModel;

import java.security.Principal;
import java.util.Optional;

public interface UserService {

    void initializeUsers();
    void registerUser(UserServiceModel serviceModel);

    Optional<UserEntity> findUserByName(String name);

    UserInfoViewModel getLoggedUserInfo(Principal principal);

    boolean isUserExistingByEmailOrUsername(String email, String username);
}

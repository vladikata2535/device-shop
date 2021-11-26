package computer.shop.service.impl;

import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.repository.UserRoleRepository;
import computer.shop.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initializeRoles() {
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        userRoleRepository.saveAll(List.of(adminRole, userRole));
    }
}

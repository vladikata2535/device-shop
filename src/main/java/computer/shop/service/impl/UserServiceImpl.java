package computer.shop.service.impl;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enumEntity.UserRoleEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import computer.shop.models.entity.enums.UserRoleEnum;
import computer.shop.models.service.UserServiceModel;
import computer.shop.models.view.UserBalanceViewModel;
import computer.shop.models.view.UserViewModel;
import computer.shop.repository.CouponRepository;
import computer.shop.repository.UserRepository;
import computer.shop.repository.UserRoleRepository;
import computer.shop.service.ComputerOfferService;
import computer.shop.service.SmartphoneOfferService;
import computer.shop.service.UserService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CouponRepository couponRepository;
    private final DeviceShopUserServiceImpl deviceShopUserService;
    private final ComputerOfferService computerOfferService;
    private final SmartphoneOfferService smartphoneOfferService;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CouponRepository couponRepository, DeviceShopUserServiceImpl deviceShopUserService, ComputerOfferService computerOfferService, SmartphoneOfferService smartphoneOfferService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.couponRepository = couponRepository;
        this.deviceShopUserService = deviceShopUserService;
        this.computerOfferService = computerOfferService;
        this.smartphoneOfferService = smartphoneOfferService;
    }

    @Override
    public void initializeUsers() {
        UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        CouponEntity couponEntityAdmin = couponRepository.findByPercentage(CouponPercentageEnum.fifty);
        CouponEntity couponEntityUser = couponRepository.findByPercentage(CouponPercentageEnum.ten);

        UserEntity admin = new UserEntity();
        admin
                .setUsername("admin")
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setAge(25)
                .setPassword(passwordEncoder.encode("test"))
                .setEmail("admin@abv.bg")
                .setBornOn(LocalDate.now())
                .setBalance(BigDecimal.valueOf(0))
                .setCountry("Bulgaria")
                .setNotActiveCoupons(Set.of(couponEntityAdmin))
                .setRoles(Set.of(adminRole, userRole));

        userRepository.save(admin);

        UserEntity gosho = new UserEntity();
        gosho
                .setUsername("gosho")
                .setFirstName("Georgi")
                .setLastName("Georgiev")
                .setAge(18)
                .setPassword(passwordEncoder.encode("test"))
                .setEmail("gosho@abv.bg")
                .setBornOn(LocalDate.now())
                .setBalance(BigDecimal.valueOf(0))
                .setCountry("Bulgaria")
                .setNotActiveCoupons(Set.of(couponEntityUser))
                .setRoles(Set.of(userRole));

        userRepository.save(gosho);
    }

    @Override
    public void registerUser(UserServiceModel serviceModel) {
        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        CouponEntity couponEntityUser = couponRepository.findByPercentage(CouponPercentageEnum.fifteen);

        UserEntity user = modelMapper.map(serviceModel, UserEntity.class);

        user.setPassword(passwordEncoder.encode(serviceModel.getPassword()));
        user.setBalance(BigDecimal.valueOf(0));
        user.setRoles(Set.of(userRole));
        user.setNotActiveCoupons(Set.of(couponEntityUser));

        user = userRepository.save(user);

        UserDetails principal = deviceShopUserService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    @Override
    public Optional<UserEntity> findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public UserBalanceViewModel getLoggedUserInfo(String name) {
        UserEntity user = findUserByName(name).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        return modelMapper.map(user, UserBalanceViewModel.class);
    }

    @Override
    public boolean isUserExistingByEmailOrUsername(String email, String username) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);

        return byUsername.isPresent() || byEmail.isPresent();
    }

    @Override
    public boolean canUserBuyProduct(Long smartphoneOfferId, Long computerOfferId, String couponName, String name) {
        double price;

        if (smartphoneOfferId == null) {
            price = computerOfferService.findOfferPriceById(computerOfferId);
        } else {
            price = smartphoneOfferService.findOfferPriceById(smartphoneOfferId);
        }

        double userBalance = getUserBalance(name);
        double result;

        if (couponName != null) {
            double percentage = Double.parseDouble(couponName.substring(0, 2));
            price = price - (price * (percentage / 100));
        }
        result = userBalance - price;

        if (result < 0) {
            return false;
        }
        return true;
    }

    @Override
    public double getUserBalance(String name) {
        return userRepository
                .findByUsername(name)
                .map(userEntity -> userEntity.getBalance().doubleValue())
                .orElseThrow(() -> new ObjectNotFoundException("User with name " + name + " not found!"));
    }

    @Override
    public UserViewModel getUserInfo(String userName) {
        return userRepository
                .findByUsername(userName)
                .map(userEntity -> modelMapper.map(userEntity, UserViewModel.class))
                .orElseThrow(() -> new ObjectNotFoundException("User with name " + userName + " not found!"));

    }

    @Override
    public void addMoneyToUser(String username) {
        UserEntity user = findUserByName(username).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        double balance = user.getBalance().doubleValue();

        balance += 1000;

        user.setBalance(BigDecimal.valueOf(balance));

        userRepository.save(user);
    }
}

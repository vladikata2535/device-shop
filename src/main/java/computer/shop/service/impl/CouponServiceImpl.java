package computer.shop.service.impl;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.UserEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import computer.shop.models.view.CouponViewModel;
import computer.shop.repository.CouponRepository;
import computer.shop.repository.UserRepository;
import computer.shop.service.CouponService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CouponServiceImpl(CouponRepository couponRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeCoupons() {
        Arrays.stream(CouponPercentageEnum.values())
                .forEach(couponPercentageEnum -> {
                    CouponEntity coupon = new CouponEntity();

                    coupon.setActivated(false);
                    coupon.setPercentage(couponPercentageEnum);
                    coupon.setDescription(String.format("This discount coupon gives you a %d%% discount on the next product you buy. You just need to activate it.", couponPercentageEnum.label));

                    couponRepository.save(coupon);
                });

    }

    @Override
    public void addCouponsToLoggedUser() {
        int randomFirst = new Random().nextInt(5 - 1 + 1) + 1;
        int randomSecond = new Random().nextInt(5 - 1 + 1) + 1;

        while (randomFirst == randomSecond){
            randomSecond = new Random().nextInt(5 - 1 + 1) + 1;
        }
        final int second = randomSecond;

        userRepository
                .findAll()
                .forEach(userEntity -> {
                    CouponEntity couponFirst = couponRepository.findById((long) randomFirst).orElseThrow(() -> new ObjectNotFoundException("Coupon not found"));
                    CouponEntity couponSecond = couponRepository.findById((long) second).orElseThrow(() -> new ObjectNotFoundException("Coupon not found"));

                    userEntity.getActiveCoupons().clear();
                    userEntity.getNotActiveCoupons().clear();
                    userEntity.getNotActiveCoupons().add(couponFirst);
                    userEntity.getNotActiveCoupons().add(couponSecond);

                    userRepository.save(userEntity);
                });
    }

    @Override
    public List<CouponViewModel> findAllCurrentLoggedUserNotActiveCoupons(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        return user
                .getNotActiveCoupons()
                .stream()
                .map(couponEntity -> {
                    CouponViewModel couponViewModel = modelMapper.map(couponEntity, CouponViewModel.class);
                    couponViewModel.setPercentageNumber(couponEntity.getPercentage().label);
                    return couponViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponViewModel> findAllCurrentLoggedUserActiveCoupons(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        return user
                .getActiveCoupons()
                .stream()
                .map(couponEntity -> {
                    CouponViewModel couponViewModel = modelMapper.map(couponEntity, CouponViewModel.class);
                    couponViewModel.setPercentageNumber(couponEntity.getPercentage().label);
                    couponViewModel.setName(couponEntity.getPercentage().label + "% COUPON");
                    return couponViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void activateCoupon(Long id, String username) {
        CouponEntity couponEntity = couponRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Coupon with id " + id + " not found!"));
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        int counter = 0;

        for(CouponEntity coupon : user.getNotActiveCoupons()){
            if(coupon.getPercentage().label.equals(couponEntity.getPercentage().label)){
                user.getNotActiveCoupons().remove(coupon);
                break;
            }
        }

        user
                .getActiveCoupons()
                .add(couponEntity);

        userRepository.save(user);
    }
}

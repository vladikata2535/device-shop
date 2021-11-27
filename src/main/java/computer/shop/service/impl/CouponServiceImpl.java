package computer.shop.service.impl;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import computer.shop.repository.CouponRepository;
import computer.shop.repository.UserRepository;
import computer.shop.service.CouponService;
import computer.shop.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public CouponServiceImpl(CouponRepository couponRepository, UserRepository userRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
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

        userRepository
                .findAll()
                .forEach(userEntity -> {
                    CouponEntity couponFirst = couponRepository.findById((long)randomFirst).orElseThrow(() -> new ObjectNotFoundException("Coupon not found"));
                    CouponEntity couponSecond = couponRepository.findById((long)randomSecond).orElseThrow(() -> new ObjectNotFoundException("Coupon not found"));

                    userEntity.getNotActiveCoupons().clear();
                    userEntity.getNotActiveCoupons().add(couponFirst);
                    userEntity.getNotActiveCoupons().add(couponSecond);

                    userRepository.save(userEntity);
                });
    }
}

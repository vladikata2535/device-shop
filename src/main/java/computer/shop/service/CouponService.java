package computer.shop.service;

import computer.shop.models.view.CouponViewModel;

import java.security.Principal;
import java.util.List;

public interface CouponService {
   void initializeCoupons();

    void addCouponsToLoggedUser();

    List<CouponViewModel> findAllCurrentLoggedUserNotActiveCoupons(String username);

    List<CouponViewModel> findAllCurrentLoggedUserActiveCoupons(String username);

    void activateCoupon(Long id, String username);
}

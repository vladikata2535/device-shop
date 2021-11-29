package computer.shop.service;

import computer.shop.models.view.CouponViewModel;

import java.security.Principal;
import java.util.List;

public interface CouponService {
   void initializeCoupons();

    void addCouponsToLoggedUser();

    List<CouponViewModel> findAllCurrentLoggedUserNotActiveCoupons(Principal principal);

    List<CouponViewModel> findAllCurrentLoggedUserActiveCoupons(Principal principal);

    void activateCoupon(Long id, Principal principal);
}

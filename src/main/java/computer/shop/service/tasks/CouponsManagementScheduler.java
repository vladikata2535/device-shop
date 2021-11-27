package computer.shop.service.tasks;

import computer.shop.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CouponsManagementScheduler {

    private final CouponService couponService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponsManagementScheduler.class);

    public CouponsManagementScheduler(CouponService couponService) {
        this.couponService = couponService;
    }

    @Scheduled(fixedDelay = 1200000)
    public void showMessage(){
        couponService.addCouponsToLoggedUser();
        LOGGER.info("Coupons added to logged user!");
    }

}

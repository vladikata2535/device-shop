package computer.shop.repository;

import computer.shop.models.entity.CouponEntity;
import computer.shop.models.entity.enums.CouponPercentageEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity,Long> {
    CouponEntity findByPercentage(CouponPercentageEnum fifteen);
}

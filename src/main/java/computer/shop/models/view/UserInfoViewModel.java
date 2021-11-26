package computer.shop.models.view;

import java.math.BigDecimal;

public class UserInfoViewModel {
    private BigDecimal balance;

    public UserInfoViewModel() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserInfoViewModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

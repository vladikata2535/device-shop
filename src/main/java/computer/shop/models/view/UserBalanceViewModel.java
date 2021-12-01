package computer.shop.models.view;

import java.math.BigDecimal;

public class UserBalanceViewModel {
    private BigDecimal balance;

    public UserBalanceViewModel() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserBalanceViewModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}

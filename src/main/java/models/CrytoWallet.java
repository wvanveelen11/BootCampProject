package models;

import java.io.Serializable;
import java.math.BigDecimal;

public  class CrytoWallet extends CryptoCurrency implements Serializable{

    private BigDecimal userCount;

    public CrytoWallet(CryptoCurrency cc, BigDecimal userCount) {
        super(cc.getId(), cc.getAcronym(),cc.getName(),cc.getPrice(),cc.getAvailable());
        this.userCount = userCount;
    }

    public BigDecimal getUserCount() {
        return userCount;
    }

    public void setUserCount(BigDecimal userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return  super.getName() +
                " Amount=" + userCount;
    }
}
